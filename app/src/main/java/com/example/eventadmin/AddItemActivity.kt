package com.example.eventadmin

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.eventadmin.databinding.ActivityAddItemBinding
import com.example.eventadmin.model.AllMenu
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class AddItemActivity : AppCompatActivity() {

    // Food item Details
    private lateinit var itemName: String
    private lateinit var itemPrice: String
    private lateinit var itemDescription: String
    private  var itemImageUri: Uri? = null

    // Firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    private val binding: ActivityAddItemBinding by lazy {
        ActivityAddItemBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // initialize FireBase
        auth = FirebaseAuth.getInstance()
        // Initialize Firebase database instance
        database = FirebaseDatabase.getInstance()

        binding.AddItemButton.setOnClickListener {
            // Get Data from Filed
            itemName = binding.itemName.text.toString().trim()
            itemPrice = binding.itemPrice.text.toString().trim()
            itemDescription = binding.description.text.toString().trim()

            if (!(itemName.isBlank()||itemPrice.isBlank()||itemDescription.isBlank())){
                uploadData()
                Toast.makeText(this, "Item Add Successfully", Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Toast.makeText(this, "Fill all the details", Toast.LENGTH_SHORT).show()
            }
        }
        binding.selectImage.setOnClickListener {
            pickImage.launch("image/*")
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun uploadData() {

        // get a reference to the "menu" node in the database
        val menuRef = database.getReference("menu")
        // Generate a unique key for the new menu item
        val newItemKey = menuRef.push().key

        if (itemImageUri != null){

            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("menu_images/${newItemKey}.jpg")
            val uploadTask =  imageRef.putFile(itemImageUri!!)

            uploadTask.addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener {
                        downloadUrl->
                    //
                    //Create a new menu item
                    val newItem = AllMenu(
                        newItemKey,
                        itemName = itemName,
                        itemPrice = itemPrice,
                        itemDescription = itemDescription,
                        itemImage = downloadUrl.toString()
                    )
                    newItemKey?.let {
                            key ->
                        menuRef.child(key).setValue(newItem).addOnSuccessListener {
                            Toast.makeText(this, "data uploaded successfully", Toast.LENGTH_SHORT).show()
                        }
                            .addOnFailureListener {
                                Toast.makeText(this, "data uploaded failed", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Image Upload failed", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(this, "Please select an image ", Toast.LENGTH_SHORT).show()
        }
    }

    private val pickImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                binding.selectedImage.setImageURI(uri)
                itemImageUri = uri
            }
        }
}