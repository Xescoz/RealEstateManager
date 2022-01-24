package com.openclassrooms.realestatemanager.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.openclassrooms.realestatemanager.databinding.FragmentItemDetailBinding
import com.openclassrooms.realestatemanager.models.Property


/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [PropertyListFragment]
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */
class PropertyDetailFragment : Fragment() {

    private val args: PropertyDetailFragmentArgs by navArgs()

    private var propertyParcel: Property? = null

    private var _binding: FragmentItemDetailBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        propertyParcel = if(args.property!=null){
            args.property!!
        }
        else{
            arguments?.getParcelable("item")
        }

        initView(propertyParcel)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun test(){
        binding.descriptionContent.text = propertyParcel!!.description
        binding.surfaceContent.text = propertyParcel!!.country}

    private fun initView(property: Property?){
        binding.descriptionContent.text = property?.description

        binding.surfaceContent.text = property?.surface
        binding.numberRoomsContent.text = property?.nbOfRooms.toString()
        binding.numberBedroomContent.text = property?.nbOfBedrooms.toString()
        binding.numberBathroomContent.text = property?.nbOfBathrooms.toString()

        binding.locationRoad.text = property?.address
        binding.locationCity.text = property?.city
        binding.locationApartment.text = property?.apartment
        binding.locationCountryCode.text = property?.postcode
        binding.locationCountry.text = property?.country

        binding.buttonEditDetail.setOnClickListener{
            val editPropertyIntent = Intent(context,EditPropertyDetailActivity::class.java)
            editPropertyIntent.putExtra("Property",property)

            editPropertyResultLauncher.launch(editPropertyIntent)
        }

        binding.photosRecyclerview.adapter = context?.let {
            property?.let { property ->
                PropertyDetailRecyclerViewAdapter(
                        property.photos,
                        it.applicationContext
                )
            }
        }
    }


    private var editPropertyResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val value = it.data?.getParcelableExtra<Property>("ActivityResult")
            initView(value)
        }
    }

}

