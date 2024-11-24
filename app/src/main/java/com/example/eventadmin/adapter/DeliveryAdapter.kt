package com.example.eventadmin.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eventadmin.databinding.DeliveryItemBinding

class DeliveryAdapter(
    private val customerNames: MutableList<String>,
    private val moneyStatus: MutableList<Boolean>
) : RecyclerView.Adapter<DeliveryAdapter.DeliveryViewHolder>() {

    // Create view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
        // Inflate the layout for each item
        val binding = DeliveryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeliveryViewHolder(binding)
    }

    // Bind data to view holder
    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
        holder.bind(position)
    }

    // Return the number of items in the list
    override fun getItemCount(): Int = customerNames.size

    // View holder for delivery items
    inner class DeliveryViewHolder(private val binding: DeliveryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                // Set customer name
                customerName.text = customerNames[position]

                // Set status text based on money status
                if (moneyStatus[position]) {
                    statusMoney.text = "Received"
                } else {
                    statusMoney.text = "Not Received"
                }

                // Define color map for status text and background color
                val colorMap = mapOf(
                    true to Color.GREEN,
                    false to Color.RED
                )

                // Set text color and background color based on money status
                val textColor = colorMap[moneyStatus[position]] ?: Color.BLACK
                statusMoney.setTextColor(textColor)
                statusColor.backgroundTintList = ColorStateList.valueOf(textColor)
            }
        }
    }
}
