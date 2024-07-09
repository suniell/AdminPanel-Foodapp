package com.example.adminwaveoffood

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adminwaveoffood.databinding.ActivityAddItemBinding
import com.example.adminwaveoffood.model.AllMenu
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class AddItemActivity : AppCompatActivity() {

    // food item details
    private lateinit var foodName: String
    private lateinit var foodPrice: String
    private lateinit var foodDescription: String
    private lateinit var foodIngredient: String
    private var foodImageUri: Uri? = null

    // firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    private val binding: ActivityAddItemBinding by lazy {
        ActivityAddItemBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //initialize Firebase
        auth = FirebaseAuth.getInstance()
        // Initialize Firebase database instance
        database = FirebaseDatabase.getInstance()

        binding.addItemButton.setOnClickListener {
            // get data from Filed
            foodName = binding.FoodName.text.toString().trim()
            foodPrice = binding.FoodPrice.text.toString().trim()
            foodDescription = binding.description.text.toString().trim()
            foodIngredient = binding.ingredient.text.toString().trim()

            if (!(foodName.isBlank() || foodPrice.isBlank() || foodDescription.isBlank() || foodIngredient.isBlank())) {
                uploadData()
                Toast.makeText(this, "Item Added Successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Fill All The Details", Toast.LENGTH_SHORT).show()
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
        // get a reference to the "menu" node in database
        val menuRef = database.getReference("menu")
        // Generate unique key for the new menu
        val newItemKey = menuRef.push().key

        if (foodImageUri != null) {

            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("Menu_Images/${newItemKey}.jpg")
            val uploadTask = imageRef.putFile(foodImageUri!!)

            uploadTask.addOnCompleteListener {
                imageRef.downloadUrl.addOnCompleteListener { downloadUrl ->
                    // Create a new menu Item
                    val newItem = AllMenu(
                        foodName = foodName,
                        foodPrice = foodPrice,
                        foodDescription = foodDescription,
                        foodIngredient = foodIngredient,
                        foodImage = downloadUrl.toString(),
                    )
                    newItemKey?.let { key ->
                        menuRef.child(key).setValue(newItem).addOnCompleteListener {
                            Toast.makeText(this, "data uploaded successfully", Toast.LENGTH_SHORT)
                                .show()

                        }
                            .addOnFailureListener {
                                Toast.makeText(this, "data upload Failed", Toast.LENGTH_SHORT)
                                    .show()

                            }

                    }

                }
            }.addOnFailureListener {
                Toast.makeText(this, "Image upload Failed", Toast.LENGTH_SHORT).show()
            }
        }else {
            Toast.makeText(this, "Please select an Image", Toast.LENGTH_SHORT).show()
        }



    }

    private val pickImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                binding.selectedImage.setImageURI(uri)
                foodImageUri = uri
            }
        }


}