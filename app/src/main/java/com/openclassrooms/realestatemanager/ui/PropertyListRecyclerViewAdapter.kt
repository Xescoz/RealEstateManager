package com.openclassrooms.realestatemanager.ui

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ItemHouseBinding
import com.openclassrooms.realestatemanager.models.Property
import com.openclassrooms.realestatemanager.stringToBitMap

class PropertyListRecyclerViewAdapter(
        private val onClickListener: View.OnClickListener,
        private val context : Context
) :
        RecyclerView.Adapter<PropertyListRecyclerViewAdapter.ViewHolder>() {


    var listProperty: List<Property> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ItemHouseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = listProperty[position]
        Log.v("Ping viewHolder",item.price.toString())

        holder.binding.itemHousePrice.text = "$"+item.price.toString()
        holder.binding.itemHouseType.text = item.propertyType
        holder.binding.itemHouseCity.text = item.city

        if(item.sold){
            holder.binding.onSale.text = "Sold"
            holder.binding.onSale.setTextColor(ContextCompat.getColor(context,R.color.red))
            holder.binding.onSaleDate.text = "Since " + item.dateOfSale
        }
        else {
            holder.binding.onSale.text = "On Sale"
            holder.binding.onSale.setTextColor(ContextCompat.getColor(context,R.color.green))
            holder.binding.onSaleDate.text = "Since " + item.date
        }

        holder.binding.itemHouseImage.setImageBitmap(item.picture.stringToBitMap())

        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount() = listProperty.size

    inner class ViewHolder(val binding: ItemHouseBinding) : RecyclerView.ViewHolder(binding.root)

}

