package com.openclassrooms.realestatemanager.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.openclassrooms.realestatemanager.DatePickerFragment
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivitySearchPropertyBinding
import com.openclassrooms.realestatemanager.models.Property
import com.openclassrooms.realestatemanager.room.*

class SearchPropertyActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private lateinit var binding: ActivitySearchPropertyBinding

    private val searchPropertyViewModel: SearchPropertyViewModel by viewModels {
        SearchPropertyViewModelFactory((this.application as PropertyApplication).propertyRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchPropertyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init(){
        binding.dateOfSaleEdit.setOnClickListener{
            showDateOfSalePickerDialog()
        }
        binding.dateEdit.setOnClickListener{
            showDatePickerDialog()
        }

        binding.confirmButton.setOnClickListener {
            confirm()
        }
    }

    private fun confirm(){
        if(binding.cityEdit.text != null){
            searchPropertyViewModel.getPropertyWhereCity(binding.cityEdit.text.toString()).observe(this, {propertyList->
                if(propertyList!=null){
                    val intent = Intent()
                    Log.v("TEST",propertyList.size.toString())
                    intent.putParcelableArrayListExtra("ActivityResult", propertyList.toArrayList())
                    setResult(RESULT_OK, intent)
                    finish()
                }

            })
        }

    }

    private fun <T> List<T>.toArrayList(): ArrayList<T>{
        return ArrayList(this)
    }


    private fun showDatePickerDialog() {
        val newFragment: DialogFragment = DatePickerFragment()
        newFragment.show(supportFragmentManager, "datePicker")
    }

    private fun showDateOfSalePickerDialog() {
        val newFragment: DialogFragment = DatePickerFragment()
        newFragment.show(supportFragmentManager, "dateOfSalePicker")
    }

    override fun onDateSet(view: DatePicker?, year: Int, monthData: Int, dayData: Int) {
        var monthToChange = monthData
        monthToChange++
        val month: String = intToString(monthToChange)
        val day: String = intToString(dayData)

        binding.dateEdit.setText(getString(R.string.date_picker, day, month, year))

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