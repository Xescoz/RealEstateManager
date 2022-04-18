package com.openclassrooms.realestatemanager.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivitySearchPropertyBinding
import com.openclassrooms.realestatemanager.room.*
import java.util.*
import kotlin.collections.ArrayList

class SearchPropertyActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchPropertyBinding
    private val calendar = Calendar.getInstance()
    private val year = calendar[Calendar.YEAR]
    private val month = calendar[Calendar.MONTH]
    private val day = calendar[Calendar.DAY_OF_MONTH]

    private val searchPropertyViewModel: SearchPropertyViewModel by viewModels {
        SearchPropertyViewModelFactory((this.application as PropertyApplication).propertyRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchPropertyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.dateOfSaleEdit.setOnClickListener {
            showDateOfSalePickerDialog()
        }
        binding.dateEdit.setOnClickListener {
            showDatePickerDialog()
        }

        binding.confirmButton.setOnClickListener {
            confirm()
        }
    }

    private fun confirm() {
        val city: String = if (binding.cityEdit.text?.isNotEmpty() == true)
            binding.cityEdit.text.toString()
        else
            ""

        val pointOfInterest: String = if (binding.pointOfInterestEdit.text?.isNotEmpty() == true)
            binding.pointOfInterestEdit.text.toString()
        else
            ""

        val date: String = if (binding.dateEdit.text?.isNotEmpty() == true)
            binding.dateEdit.text.toString()
        else
            ""

        val dateOfSale: String = if (binding.dateOfSaleEdit.text?.isNotEmpty() == true)
            binding.dateOfSaleEdit.text.toString()
        else
            ""
        val minPrice = binding.priceSlider.values[0].toInt()
        val maxPrice = binding.priceSlider.values[1].toInt()

        val minSize = binding.sizeSlider.values[0].toInt()
        val maxSize = binding.sizeSlider.values[1].toInt()


        if (binding.photosEdit.text?.isNotEmpty() == true)
            searchPropertyViewModel.getPropertyMatchWithPhotos(city, binding.photosEdit.text.toString().toInt(), pointOfInterest, date, dateOfSale, minPrice, maxPrice, minSize, maxSize).observe(this) { propertyList ->
                if (propertyList != null) {
                    val intent = Intent()
                    Log.v("City", binding.cityEdit.text.toString())
                    intent.putParcelableArrayListExtra("ActivityResult", propertyList.toArrayList())
                    setResult(RESULT_OK, intent)
                    finish()
                }

            }
        else
            searchPropertyViewModel.getPropertyMatchWithoutPhotos(city, pointOfInterest, date, dateOfSale, minPrice, maxPrice, minSize, maxSize).observe(this) { propertyList ->
                if (propertyList != null) {
                    val intent = Intent()
                    Log.v("City", binding.cityEdit.text.toString())
                    intent.putParcelableArrayListExtra("ActivityResult", propertyList.toArrayList())
                    setResult(RESULT_OK, intent)
                    finish()
                }

            }


    }

    private fun <T> List<T>.toArrayList(): ArrayList<T> {
        return ArrayList(this)
    }


    private fun showDatePickerDialog() {
        DatePickerDialog(this, datePicker, year, month, day).show()
    }

    private fun showDateOfSalePickerDialog() {
        DatePickerDialog(this, dateOfSalePicker, year, month, day).show()
    }

    private val datePicker = DatePickerDialog.OnDateSetListener { _, year, monthData, dayData ->
        var monthToChange = monthData
        monthToChange++
        val month: String = intToString(monthToChange)
        val day: String = intToString(dayData)

        binding.dateEdit.setText(getString(R.string.date_picker, day, month, year))
    }

    private val dateOfSalePicker = DatePickerDialog.OnDateSetListener { _, year, monthData, dayData ->
        var monthToChange = monthData
        monthToChange++
        val month: String = intToString(monthToChange)
        val day: String = intToString(dayData)

        binding.dateOfSaleEdit.setText(getString(R.string.date_picker, day, month, year))
    }

    private fun intToString(intToChange: Int): String {
        return if (intToChange < 10) "0$intToChange" else "" + intToChange
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