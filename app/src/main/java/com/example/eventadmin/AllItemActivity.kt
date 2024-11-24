package com.example.eventadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eventadmin.adapter.MenuItemAdapter
import com.example.eventadmin.databinding.ActivityAllItemBinding
import com.example.eventadmin.model.AllMenu
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AllItemActivity : AppCompatActivity() {

    private lateinit var  databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private var menuItems: ArrayList<AllMenu> = ArrayList()
    private val binding : ActivityAllItemBinding by lazy {
        ActivityAllItemBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        databaseReference = FirebaseDatabase.getInstance().reference
        retrieveMenuItem()

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun retrieveMenuItem() {
        database = FirebaseDatabase.getInstance()
        val itemRef: DatabaseReference = database.reference.child("menu")

        // fetch data from data base
        itemRef.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                // Clear existing data before populating
                menuItems.clear()

                // loop for through each food item
                for (itemSnapshot in  snapshot.children){
                    val menuItem = itemSnapshot.getValue(AllMenu::class.java)
                    menuItem?.let {
                        menuItems.add(it)
                    }
                }
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("DatabaseError", "Error: ${error.message}")
            }
        })
    }
    private fun setAdapter() {

        val adapter = MenuItemAdapter(this@AllItemActivity,menuItems,databaseReference){ position ->
            deleteMenuItems(position)
        }
        binding.MenuRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.MenuRecyclerView.adapter= adapter
    }

    private fun deleteMenuItems(position: Int) {
        val menuItemToDelete= menuItems[position]
        val menuItemKey = menuItemToDelete.key
        val itemMenuReference = database.reference.child("menu").child(menuItemKey!!)
        itemMenuReference.removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful){
                menuItems.removeAt(position)
                binding.MenuRecyclerView.adapter?.notifyItemRemoved(position)
            }else
            {
                Toast.makeText(this, "Item not Deleted", Toast.LENGTH_SHORT).show()
            }
        }
    }
}