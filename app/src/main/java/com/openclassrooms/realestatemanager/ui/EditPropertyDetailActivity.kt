package com.openclassrooms.realestatemanager.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Notification
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.material.textfield.TextInputEditText
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivityEditPropertyDetailBinding
import com.openclassrooms.realestatemanager.models.Photo
import com.openclassrooms.realestatemanager.models.Property
import com.openclassrooms.realestatemanager.room.*
import com.openclassrooms.realestatemanager.stringToBitMap
import java.io.ByteArrayOutputStream
import java.time.LocalDate


class EditPropertyDetailActivity : AppCompatActivity() {

    private val propertyViewModel: PropertyViewModel by viewModels {
        PropertyViewModelFactory((this.application as PropertyApplication).propertyRepository, (this.application as PropertyApplication).agentRepository)
    }

    private lateinit var binding: ActivityEditPropertyDetailBinding

    private var property: Property? = null

    private var isCreate: Boolean = false

    private var isMainPicture: Boolean = false

    private lateinit var adapter: EditPropertyDetailRecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditPropertyDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        property = intent.getParcelableExtra("Property")

        isCreate = intent.getBooleanExtra("isCreate", false)

        if (isCreate) {
            property = Property(null, "", "", "", 0, "",
                    0, 0, "", "", "", "", false, null, "",
                    "", "", "", "", arrayListOf(), "", "")
        }

        init()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun init() {

        val onLongClickListener = View.OnLongClickListener { itemView ->
            val position = itemView.tag as Int
            createDeleteDialog(position)
            true
        }

        binding.descriptionEdit.setText(property!!.description)
        binding.surfaceEdit.setText(property!!.surface.toString())
        binding.numberRoomsEdit.setText(property!!.nbOfRooms)
        binding.numberBedroomEdit.setText(property!!.nbOfBedrooms)
        binding.numberBathroomEdit.setText(property!!.nbOfBathrooms)
        binding.locationRoadEdit.setText(property!!.address)
        binding.locationCityEdit.setText(property!!.city)
        binding.locationApartmentEdit.setText(property!!.apartment)
        binding.locationCountryCodeEdit.setText(property!!.postcode)
        binding.locationCountryEdit.setText(property!!.country)
        binding.priceEdit.setText(property!!.price.toString())
        binding.typeOfPropertyEdit.setText(property!!.propertyType)
        binding.agentEdit.setText(property!!.agent)

        if (property!!.picture.isEmpty())
            binding.pictureProperty.setImageDrawable(getDrawable(R.drawable.ic_baseline_add_property))
        else
            binding.pictureProperty.setImageBitmap(property!!.picture.stringToBitMap())

        binding.pictureProperty.setOnLongClickListener {
            isMainPicture = true
            createCameraGalleryDialog()
            true
        }

        if (property!!.sold) {
            binding.switchSold.isClickable = false
            binding.switchSold.isChecked = true
        } else {
            binding.switchSold.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    property!!.sold = true
                    property!!.dateOfSale = LocalDate.now().toString()
                } else {
                    property!!.sold = false
                    property!!.dateOfSale = null
                }
            }
        }




        adapter = EditPropertyDetailRecyclerViewAdapter(property!!.photos, onLongClickListener)
        binding.photosRecyclerview.adapter = adapter

        binding.buttonAddPhotos.setOnClickListener {
            isMainPicture = false
            createCameraGalleryDialog()
        }

        binding.confirmButton.setOnClickListener {
            updateProperty()
        }
    }

    private fun updateProperty() {
        val propertyUpdated = property
        propertyUpdated!!.description = binding.descriptionEdit.text.toString()
        propertyUpdated.surface = binding.surfaceEdit.text.toString().toInt()
        propertyUpdated.nbOfRooms = binding.numberRoomsEdit.text.toString()
        propertyUpdated.nbOfBedrooms = binding.numberBedroomEdit.text.toString()
        propertyUpdated.nbOfBathrooms = binding.numberBathroomEdit.text.toString()
        propertyUpdated.address = binding.locationRoadEdit.text.toString()
        propertyUpdated.city = binding.locationCityEdit.text.toString()
        propertyUpdated.apartment = binding.locationApartmentEdit.text.toString()
        propertyUpdated.postcode = binding.locationCountryCodeEdit.text.toString()
        propertyUpdated.country = binding.locationCountryEdit.text.toString()
        propertyUpdated.price = binding.priceEdit.text.toString().toInt()
        propertyUpdated.propertyType = binding.typeOfPropertyEdit.text.toString()
        propertyUpdated.agent = binding.agentEdit.text.toString()
        property?.numberOfPhotos = property?.photos!!.size

        if (isCreate) {
            propertyViewModel.insert(propertyUpdated)
            createNotification()
        }
        else {
            val intent = Intent()
            intent.putExtra("ActivityResult", propertyUpdated)
            setResult(RESULT_OK, intent)

            propertyViewModel.updateProperty(propertyUpdated)
        }
        finish()

    }

    private fun createDeleteDialog(position: Int) {

        val alertDialog: AlertDialog = this.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(R.string.confirm
                ) { _, _ ->
                    property!!.photos!!.removeAt(position)
                    adapter.updateList(property!!.photos)
                }
                setNegativeButton(R.string.cancel
                ) { _, _ ->
                    Toast.makeText(context, "Photo not deleted", Toast.LENGTH_SHORT).show()
                }
                setMessage(R.string.delete_dialog)
            }
            builder.create()
        }
        alertDialog.show()
    }


    private fun createCameraGalleryDialog() {

        val alertDialog: AlertDialog = this.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(R.string.gallery_confirmation
                ) { _, _ ->
                    val actionPickIntent = Intent(Intent.ACTION_PICK)
                    actionPickIntent.type = "image/*"
                    resultLauncherGallery.launch(actionPickIntent)
                }
                setNegativeButton(R.string.camera_confirmation
                ) { _, _ ->
                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    resultLauncherCamera.launch(cameraIntent)
                }
                setMessage(R.string.camera_gallery_dialog)
            }
            builder.create()
        }
        alertDialog.show()
    }

    @SuppressLint("InflateParams")
    private fun createDescriptionDialog(image: String) {
        val inflater = this.layoutInflater
        val inflate = inflater.inflate(R.layout.description_dialog, null)
        val dialogDescriptionEditText: TextInputEditText = inflate.findViewById(R.id.description_dialog_edit)

        val alertDialog: AlertDialog = this.let {
            val builder = AlertDialog.Builder(it)

            builder.apply {
                setView(inflate)
                setPositiveButton(R.string.confirm
                ) { _, _ ->
                    val photo = Photo(image, dialogDescriptionEditText.text.toString())
                    property!!.photos!!.add(photo)
                    adapter.updateList(property!!.photos)

                }
            }

            // Create the AlertDialog
            builder.create()
        }
        alertDialog.show()

    }

    private fun createNotification(){

        val builder = NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setContentTitle("Real Estate Manager")
                .setContentText("You're new property has been successfully added")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(0, builder.build())
        }

    }

    private fun editPicture(bitmap: Bitmap) {
        if (isMainPicture) {
            binding.pictureProperty.setImageBitmap(bitmap)
            property!!.picture = bitmapToString(bitmap)
        } else
            createDescriptionDialog(bitmapToString(bitmap))
    }

    private var resultLauncherCamera = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val bitmap: Bitmap = data!!.extras!!.get("data") as Bitmap
            editPicture(bitmap)
        }
    }

    private var resultLauncherGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data
            val uri: Uri? = data!!.data

            val source: ImageDecoder.Source = ImageDecoder.createSource(this.contentResolver, uri!!)
            val bitmap: Bitmap = ImageDecoder.decodeBitmap(source)
            editPicture(bitmap)
        }
    }

    private fun bitmapToString(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    override fun onResume() {
        super.onResume()
        (this as AppCompatActivity).supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (this as AppCompatActivity).supportActionBar!!.show()
    }
}