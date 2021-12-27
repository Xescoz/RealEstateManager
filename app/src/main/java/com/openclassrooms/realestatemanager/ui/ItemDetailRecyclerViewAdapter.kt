package com.openclassrooms.realestatemanager.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.databinding.ItemPhotosBinding

class ItemDetailRecyclerViewAdapter(
        private val values: List<String>,
        private val context : Context
) :
        RecyclerView.Adapter<ItemDetailRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ItemPhotosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        /*
        Glide.with(context)
                .load(item.picture)
                .into(holder.binding.itemHouseImage)
                .onLoadFailed(context.getDrawable(R.drawable.house))*/
    }

    override fun getItemCount() = values.size

    inner class ViewHolder(binding: ItemPhotosBinding) : RecyclerView.ViewHolder(binding.root) {
        val binding = binding;
    }

}