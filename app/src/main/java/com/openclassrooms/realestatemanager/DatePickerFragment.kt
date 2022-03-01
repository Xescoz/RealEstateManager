package com.openclassrooms.realestatemanager

import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment:DialogFragment() {

    private var mActivity: Activity? = null
    private var mListener: OnDateSetListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Activity) mActivity = context
        mListener = context as OnDateSetListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]
        return DatePickerDialog(mActivity!!, mListener, year, month, day)
    }
}