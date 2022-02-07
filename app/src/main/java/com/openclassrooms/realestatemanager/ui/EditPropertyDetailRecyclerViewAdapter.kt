package com.openclassrooms.realestatemanager.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.databinding.ItemPhotosBinding
import com.openclassrooms.realestatemanager.models.Photo
import com.openclassrooms.realestatemanager.stringToBitMap


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
        holder.binding.photoImage.setImageBitmap(photo.picture.stringToBitMap())

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

    inner class ViewHolder(val binding: ItemPhotosBinding) : RecyclerView.ViewHolder(binding.root)

}