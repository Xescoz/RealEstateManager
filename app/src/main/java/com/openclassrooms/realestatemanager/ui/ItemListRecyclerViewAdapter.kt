package com.openclassrooms.realestatemanager.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ItemHouseBinding
import com.openclassrooms.realestatemanager.models.Property

class ItemListRecyclerViewAdapter(
        private val values: List<Property>,
        private val onClickListener: View.OnClickListener,
        private val onContextClickListener: View.OnContextClickListener,
        private val context : Context
) :
        RecyclerView.Adapter<ItemListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ItemHouseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.binding.itemHousePrice.text = "$"+item.price.toString()
        holder.binding.itemHouseType.text = item.propertyType
        holder.binding.itemHouseCity.text = item.city

        if(item.sold){
            holder.binding.onSale.text = "Sold"
            holder.binding.onSale.setTextColor(context.resources.getColor(R.color.red))
            holder.binding.onSaleDate.text = "Since " + item.dateOfSale
        }
        else {
            holder.binding.onSale.text = "On Sale"
            holder.binding.onSale.setTextColor(context.resources.getColor(R.color.green))
            holder.binding.onSaleDate.text = "Since " + item.date
        }

        Glide.with(context)
                .load(item.picture)
                .into(holder.binding.itemHouseImage)
                .onLoadFailed(context.getDrawable(R.drawable.house))


        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setOnContextClickListener(onContextClickListener)
            }
        }
    }

    override fun getItemCount() = values.size

    inner class ViewHolder(binding: ItemHouseBinding) : RecyclerView.ViewHolder(binding.root) {
        val binding = binding;
    }

}
