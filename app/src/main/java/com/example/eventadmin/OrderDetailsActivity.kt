package com.example.eventadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eventadmin.adapter.OrderDetailsAdapter
import com.example.eventadmin.databinding.ActivityOrderDetailsBinding
import com.example.eventadmin.model.OrderDetails

class OrderDetailsActivity : AppCompatActivity() {
    private val binding: ActivityOrderDetailsBinding by lazy {
        ActivityOrderDetailsBinding.inflate(layoutInflater)
    }

    private var userName: String? = null
    private var address: String? = null
    private var phoneNumber: String? = null
    private var totalPrice: String? = null
    private var itemNames: ArrayList<String> = arrayListOf()
    private var itemImages: ArrayList<String> = arrayListOf()
    private var itemQuantity: ArrayList<Int> = arrayListOf()
    private var itemPrices: ArrayList<String> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backeButton.setOnClickListener {
            finish()
        }
        getDataFromIntent()
    }

    private fun getDataFromIntent() {

        val receivedOrderDetails = intent.getSerializableExtra("UserOrderDetails") as OrderDetails
        receivedOrderDetails?.let { orderDetails ->
            userName = receivedOrderDetails.userName
            itemNames = receivedOrderDetails.itemNames as ArrayList<String>
            itemImages = receivedOrderDetails.itemImages as ArrayList<String>
            itemQuantity = receivedOrderDetails.itemQuantities as ArrayList<Int>
            address = receivedOrderDetails.address
            phoneNumber = receivedOrderDetails.phoneNumber
            itemPrices = receivedOrderDetails.itemPrices as ArrayList<String>
            totalPrice = receivedOrderDetails.totalPrice

            setUserDetail()
            setAdapter()
        }

    }


    private fun setUserDetail() {
        binding.name.text = userName
        binding.address.text = address
        binding.phone.text = phoneNumber
        binding.totalPay.text = totalPrice
    }

    private fun setAdapter() {
        binding.orderDetailRecyclerVew.layoutManager = LinearLayoutManager(this)
        val adapter = OrderDetailsAdapter(this,itemNames,itemImages,itemQuantity,itemPrices)
        binding.orderDetailRecyclerVew.adapter = adapter
    }
}