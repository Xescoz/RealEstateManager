package com.openclassrooms.realestatemanager.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.databinding.ItemPhotosBinding
import com.openclassrooms.realestatemanager.models.Photo
import com.openclassrooms.realestatemanager.stringToBitMap


class PropertyDetailRecyclerViewAdapter(
        private var photos: List<Photo>
) :
        RecyclerView.Adapter<PropertyDetailRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ItemPhotosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = photos[position]

        holder.binding.photoDescription.text = photo.description
        holder.binding.photoImage.setImageBitmap(photo.picture.stringToBitMap())

    }
    override fun getItemCount() = photos.size

    inner class ViewHolder(val binding: ItemPhotosBinding) : RecyclerView.ViewHolder(binding.root)

}