package com.example.eventadmin.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eventadmin.databinding.OrderDetailItemBinding

class OrderDetailsAdapter(
    private var context: Context,
    private var itemNames: ArrayList<String>,
    private var itemImages: ArrayList<String>,
    private var itemQuantitys: ArrayList<Int>,
    private var itemPrices: ArrayList<String>
) : RecyclerView.Adapter<OrderDetailsAdapter.OrderDetailsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailsViewHolder {
        val binding =
            OrderDetailItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderDetailsViewHolder(binding)
    }


    override fun onBindViewHolder(holder: OrderDetailsViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = itemNames.size
    inner  class OrderDetailsViewHolder(private val binding: OrderDetailItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                itemNames.text = this@OrderDetailsAdapter.itemNames[position]

                itemQuantity.text = itemQuantitys[position].toString()
                val uriString = this@OrderDetailsAdapter.itemImages[position]
                val uri = Uri.parse(uriString)
                Glide.with(context).load(uri).into(itemImages)
                itemPrice.text = itemPrices[position]
            }
        }

    }
}