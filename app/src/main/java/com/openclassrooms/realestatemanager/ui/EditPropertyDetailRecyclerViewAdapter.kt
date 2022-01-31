package com.openclassrooms.realestatemanager.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.databinding.ItemPhotosBinding
import com.openclassrooms.realestatemanager.models.Photo


class EditPropertyDetailRecyclerViewAdapter(
        private var photos: List<Photo>,
        private val onLongClickListener: View.OnLongClickListener
) :
        RecyclerView.Adapter<EditPropertyDetailRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ItemPhotosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = photos[position]

        holder.binding.photoDescription.text = photo.description
        holder.binding.photoImage.setImageBitmap(stringToBitMap(photo.picture))

        with(holder.itemView) {
            tag = position
            setOnLongClickListener(onLongClickListener)
        }

    }

    fun updateList(picturesList: List<Photo>) {
        this.photos = picturesList
        notifyDataSetChanged()
    }

    override fun getItemCount() = photos.size

    private fun stringToBitMap(encodedString: String): Bitmap? {
        val encodeByte: ByteArray = Base64.decode(encodedString, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
    }

    inner class ViewHolder(val binding: ItemPhotosBinding) : RecyclerView.ViewHolder(binding.root)

}