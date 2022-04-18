package com.openclassrooms.realestatemanager.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivitySimulatorBinding


class SimulatorActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySimulatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimulatorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listener()

    }

    private fun listener() {
        binding.confirmButton.setOnClickListener {

            val loanAmount = binding.loanAmountSlider.value.toInt()
            val loanDuration = binding.loanDurationSlider.value.toInt()

            val result = (loanAmount * (1.25 / 12)) / loanDuration

            //(loanAmount*(1.25/12))/1+(1.25/12).pow(-12*loanDuration)

            createLoanDialog(result.toInt())
        }
    }

    @SuppressLint("InflateParams")
    private fun createLoanDialog(result: Int) {
        val alertDialog: AlertDialog = this.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(R.string.confirm
                ) { _, _ ->
                    finish()
                }
                setMessage(getString(R.string.loan_string_result, result.toString()))
            }
            builder.create()
        }
        alertDialog.show()
    }
}