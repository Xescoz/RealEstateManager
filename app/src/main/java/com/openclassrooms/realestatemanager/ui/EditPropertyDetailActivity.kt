package com.openclassrooms.realestatemanager.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivityEditPropertyDetailBinding
import com.openclassrooms.realestatemanager.models.Property

class EditPropertyDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditPropertyDetailBinding

    private var property: Property? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditPropertyDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        property = intent.getParcelableExtra("Property")

        init()
    }


    private fun init(){

        binding.descriptionEdit.setText(property!!.description)
        binding.surfaceEdit.setText(property!!.surface)
        binding.numberRoomsEdit.setText(property!!.nbOfRooms.toString())
        binding.numberBedroomEdit.setText(property!!.nbOfBedrooms.toString())
        binding.numberBathroomEdit.setText(property!!.nbOfBathrooms.toString())
        binding.locationRoadEdit.setText(property!!.address)
        binding.locationCityEdit.setText(property!!.city)
        binding.locationApartmentEdit.setText(property!!.apartment)
        binding.locationCountryCodeEdit.setText(property!!.postcode)
        binding.locationCountryEdit.setText(property!!.country)

        binding.photosRecyclerview.adapter = this.let {
            property?.let { property ->
                PropertyDetailRecyclerViewAdapter(
                        property.photos,
                        it.applicationContext
                )
            }
        }

        binding.buttonAddPhotos.setOnClickListener{
            createCameraGalleryDialog()
        }

        binding.confirmButton.setOnClickListener {
            updateProperty()
        }
    }

    private fun updateProperty(){
        val propertyUpdated = property
        propertyUpdated!!.description = binding.descriptionInput.editText.toString()
        propertyUpdated.surface = binding.surfaceInput.editText.toString()
        propertyUpdated.nbOfRooms = binding.numberRoomsInput.editText.toString().toInt()
        propertyUpdated.nbOfBedrooms = binding.numberBedroomInput.editText.toString().toInt()
        propertyUpdated.nbOfBathrooms = binding.numberBathroomInput.editText.toString().toInt()
        propertyUpdated.address = binding.locationRoadInput.editText.toString()
        propertyUpdated.city = binding.locationCityInput.editText.toString()
        propertyUpdated.apartment = binding.locationApartmentInput.editText.toString()
        propertyUpdated.postcode = binding.locationCountryCodeInput.editText.toString()
        propertyUpdated.country = binding.locationCountryInput.editText.toString()

        val intent = Intent()
        intent.putExtra("ActivityResult", propertyUpdated)
        setResult(RESULT_OK, intent)
        finish()

    }


    private fun createCameraGalleryDialog(){

        val alertDialog: AlertDialog = this.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(R.string.gallery_confirmation,
                        DialogInterface.OnClickListener { dialog, id ->
                            val actionPickIntent = Intent(Intent.ACTION_PICK)
                            actionPickIntent.type = "image/*"
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

        alertDialog.show()

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