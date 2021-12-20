package com.openclassrooms.realestatemanager.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.openclassrooms.realestatemanager.databinding.FragmentItemDetailBinding
import com.openclassrooms.realestatemanager.models.Property

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [ItemListFragment]
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */
class ItemDetailFragment : Fragment() {

    private val args: ItemDetailFragmentArgs by navArgs()

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
        initView()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView(){
        binding.descriptionContent.text = propertyParcel?.description

        binding.surfaceContent.text = propertyParcel?.surface
        binding.numberRoomsContent.text = propertyParcel?.nbOfRooms.toString()
        binding.numberBedroomContent.text = propertyParcel?.nbOfBedrooms.toString()
        binding.numberBathroomContent.text = propertyParcel?.nbOfBathrooms.toString()

        binding.locationRoad.text = propertyParcel?.address
        binding.locationCity.text = propertyParcel?.city
        binding.locationApartment.text = propertyParcel?.apartment
        binding.locationCountryCode.text = propertyParcel?.postcode
        binding.locationCountry.text = propertyParcel?.country
    }
}