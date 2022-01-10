package com.openclassrooms.realestatemanager.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ItemPhotosBinding
import com.openclassrooms.realestatemanager.models.Photo

class ItemDetailRecyclerViewAdapter(
        private val photos: List<Photo>,
        private val context : Context
) :
        RecyclerView.Adapter<ItemDetailRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ItemPhotosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = photos[position]

        holder.binding.photoDescription.text = photo.description

        Glide.with(context)
                .load(photo.path)
                .into(holder.binding.photoImage)
                .onLoadFailed(context.getDrawable(R.drawable.house))
    }

    override fun getItemCount() = photos.size

    inner class ViewHolder(val binding: ItemPhotosBinding) : RecyclerView.ViewHolder(binding.root)

}