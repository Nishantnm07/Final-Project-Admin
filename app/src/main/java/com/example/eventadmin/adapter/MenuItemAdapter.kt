package com.example.eventadmin.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eventadmin.databinding.ItemItemBinding
import com.example.eventadmin.model.AllMenu
import com.google.firebase.database.DatabaseReference

class MenuItemAdapter(
    private val context: Context,
    private val menuList: ArrayList<AllMenu>,
   private val databaseReference: DatabaseReference,
    private val onDeleteClickListener:(position :Int) ->Unit
) : RecyclerView.Adapter<MenuItemAdapter.AddItemViewHolder>() {
    private val itemQuantities = IntArray(menuList.size) { 1 }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        val binding = ItemItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int =menuList.size

    inner class AddItemViewHolder(private val binding: ItemItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantities[position]
                val menuItem = menuList[position]
                val uriString = menuItem.itemImage
                val uri = Uri.parse(uriString)
                itemNameTextView.text = menuItem.itemName
                priceTextView.text = menuItem.itemPrice
                Glide.with(context).load(uri).into(itemImageView)
                quantityTextVIew.text = quantity.toString()
                minusButton.setOnClickListener {
                    decreaseQuantitiy(position)
                }
                deleteButton.setOnClickListener {
                    onDeleteClickListener(position)
                }
                pluseButton.setOnClickListener {
                    increaseQuantitiy(position)
                }
            }
        }

        private fun increaseQuantitiy(position: Int) {
            if (itemQuantities[position] < 10) {
                itemQuantities[position]++
                binding.quantityTextVIew.text = itemQuantities[position].toString()
            }
        }

        private fun decreaseQuantitiy(position: Int) {
            if (itemQuantities[position] > 1) {
                itemQuantities[position]--
                binding.quantityTextVIew.text = itemQuantities[position].toString()
            }
        }

        private fun deleteQuantitiy(position: Int) {
            menuList.removeAt(position)
            menuList.removeAt(position)
            menuList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, menuList.size)
        }

    }
}