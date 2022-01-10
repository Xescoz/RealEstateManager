package com.openclassrooms.realestatemanager.ui

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.navArgs
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentItemDetailBinding
import com.openclassrooms.realestatemanager.dialog.CameraGalleryDialogFragment
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

        binding.buttonAddPhotos.setOnClickListener{
            //val cameraGalleryDialogFragmentDialog = CameraGalleryDialogFragment.newInstance(propertyParcel!!.id)
            //cameraGalleryDialogFragmentDialog.show(childFragmentManager, CameraGalleryDialogFragment.TAG)
            createCameraGalleryDialog()
        }

        binding.photosRecyclerview.adapter = context?.let {
            propertyParcel?.let { property ->
                ItemDetailRecyclerViewAdapter(
                        property.photos,
                        it.applicationContext
                )
            }
        }
    }

    fun createCameraGalleryDialog(){

        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(R.string.gallery_confirmation,
                        DialogInterface.OnClickListener { dialog, id ->
                            val actionPickIntent = Intent(Intent.ACTION_PICK)
                            resultLauncherGallery.launch(actionPickIntent)
                        })
                setNegativeButton(R.string.camera_confirmation,
                        DialogInterface.OnClickListener { dialog, id ->
                            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            resultLauncherCamera.launch(cameraIntent)
                        })
                setMessage(R.string.camera_gallery_dialog)
            }

            // Create the AlertDialog
            builder.create()
        }

        alertDialog?.show()

    }

    private var resultLauncherCamera = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data

            //data.extras.get("data") as Bitmap
        }
    }

    private var resultLauncherGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data


        }
    }

}

