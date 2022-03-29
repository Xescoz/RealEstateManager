package com.openclassrooms.realestatemanager.ui

import android.app.Activity
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentItemDetailBinding
import com.openclassrooms.realestatemanager.models.Property
import kotlin.properties.Delegates


/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [PropertyListFragment]
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */
class PropertyDetailFragment : Fragment(), OnMapReadyCallback {

    private val args: PropertyDetailFragmentArgs by navArgs()

    private var propertyParcel: Property? = null

    private var _binding: FragmentItemDetailBinding? = null

    private val binding get() = _binding!!

    private var isPhone = false

    private lateinit var mMap: GoogleMap

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
            isPhone = true
            args.property!!
        }
        else{

            arguments?.getParcelable("item")
        }

        val mapFragment = childFragmentManager.findFragmentById(R.id.map_image) as SupportMapFragment
        mapFragment.getMapAsync(this)

        initView(propertyParcel)



    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        var propertyLocation = LatLng(48.864716, 2.349014)
        if(propertyParcel!=null){
            propertyLocation = getLatLngFromAddress(propertyParcel?.address+", "+propertyParcel?.city)
        }

        mMap.addMarker(MarkerOptions().position(propertyLocation))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(propertyLocation, 16F))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView(property: Property?){
        binding.descriptionContent.text = property?.description

        binding.surfaceContent.text = resources.getString(R.string.surface_string,property?.surface)
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
                PropertyDetailRecyclerViewAdapter(property.photos)
            }
        }
    }

    private fun getLatLngFromAddress(strAddress: String): LatLng {
        val addressList: List<Address?>
        val coder = Geocoder(activity)

        addressList = coder.getFromLocationName(strAddress, 1)
        val address = addressList[0]

        return LatLng(address!!.latitude, address.longitude)

    }


    private var editPropertyResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val value = it.data?.getParcelableExtra<Property>("ActivityResult")
            initView(value)
        }
    }

    override fun onResume() {
        super.onResume()
        if(isPhone)
            (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        if(isPhone)
            (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }

}

