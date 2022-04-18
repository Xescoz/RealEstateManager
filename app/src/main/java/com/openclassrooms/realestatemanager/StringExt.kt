package com.openclassrooms.realestatemanager

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

fun String.stringToBitMap(): Bitmap? {
    val encodeByte: ByteArray = Base64.decode(this, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
}