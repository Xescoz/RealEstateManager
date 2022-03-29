package com.openclassrooms.realestatemanager.ui

import android.Manifest
import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentMapBinding
import com.openclassrooms.realestatemanager.models.Property
import com.openclassrooms.realestatemanager.room.PropertyApplication
import com.openclassrooms.realestatemanager.room.PropertyViewModel
import com.openclassrooms.realestatemanager.room.PropertyViewModelFactory
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

class MapFragment: Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private val propertyViewModel: PropertyViewModel by viewModels {
        PropertyViewModelFactory((activity?.application as PropertyApplication).propertyRepository,(activity?.application as PropertyApplication).agentRepository)
    }


    private var mMap: GoogleMap? = null
    private var location : Location? = null
    private var _binding: FragmentMapBinding? = null

    private val binding get() = _binding!!
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMapBinding.inflate(inflater, container, false)



        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        getLocationPermission()

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = childFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        moveCameraToCurrentPosition(location)

        createMarkers()

        mMap!!.setOnMarkerClickListener(this)
    }

    private fun moveCameraToCurrentPosition(location: Location?){

        if(location!=null){
            val userLatLng = LatLng(location.latitude,location.longitude)
            mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng,13F))
        }
        else{
            val paris = LatLng(48.864716, 2.349014)
            mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(paris,13F))
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    /** Check if location permission is enabled and ask user to turn on if not  */
    @AfterPermissionGranted(1)
    private fun getLocationPermission() {
        val perms = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        if (EasyPermissions.hasPermissions(requireContext(), *perms)) {
            getDeviceLocation()
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.location_rationale),
                    1, *perms)
            Toast.makeText(requireContext(), R.string.no_location_permission, Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getDeviceLocation(){
        fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    this.location = location
                    if (mMap!=null)
                        moveCameraToCurrentPosition(location)
                }
    }

    private fun createMarkers(){
        propertyViewModel.allProperty.observe(this, { propertyList ->
            var marker: Marker?
            for(property : Property in propertyList){
                val propertyLocation = getLatLngFromAddress(property.address+", "+property.city)
                marker = mMap!!.addMarker(MarkerOptions().position(propertyLocation))
                marker!!.tag = property
            }

        })
    }

    private fun getLatLngFromAddress(strAddress: String): LatLng {
        val addressList: List<Address?>
        val coder = Geocoder(requireContext())

        addressList = coder.getFromLocationName(strAddress, 1)
        val address = addressList[0]

        return LatLng(address!!.latitude, address.longitude)

    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val property = marker.tag as Property?

        val action = MapFragmentDirections.showItemDetail().setProperty(property)
        binding.map.findNavController().navigate(action)

        return false
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }

}