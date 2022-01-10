package com.openclassrooms.realestatemanager.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Photo(
        var path: String,
        var description: String) : Parcelable
