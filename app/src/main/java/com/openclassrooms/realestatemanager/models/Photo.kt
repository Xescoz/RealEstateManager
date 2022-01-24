package com.openclassrooms.realestatemanager.models

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Photo(
        var picture: String,
        var description: String) : Parcelable
