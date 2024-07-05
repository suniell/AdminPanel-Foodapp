package com.example.adminwaveoffood

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adminwaveoffood.databinding.ActivitySignUpBinding
import com.example.adminwaveoffood.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class SignUpActivity : AppCompatActivity() {

    private lateinit var email : String
    private lateinit var password : String
    private lateinit var userName : String
    private lateinit var nameOfResturant : String
    private lateinit var auth : FirebaseAuth
    private lateinit var database : DatabaseReference

    lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //initialize firebase auth
        auth = Firebase.auth
        //initialize firebase database
        database = Firebase.database.reference



    val locationList = arrayOf("jaipur", "Odissa", "Bundi", "Sikar")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,locationList)
        val autoCompleteTextview = binding.listOfLocation
        autoCompleteTextview.setAdapter(adapter)

        binding.createUserButton.setOnClickListener {
            //get text from EditText
            userName = binding.nameOfOwner.text.toString().trim()
            nameOfResturant = binding.nameOfRestaurant.text.toString().trim()
            email = binding.emailorPhoneNumber.text.toString().trim()
            password = binding.password1.text.toString().trim()

            if (userName.isBlank() || nameOfResturant.isBlank() || email.isBlank() || password.isBlank()){
                Toast.makeText(this, "Please Fill All Details", Toast.LENGTH_SHORT).show()
            }else{
                createAccount(email,password)
            }


        }

        binding.alreadyhaveaccountbutton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }





    }

    private fun createAccount(email: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            task ->
            if (task.isSuccessful){
                Toast.makeText(this, "account created successfully", Toast.LENGTH_SHORT).show()
                saveUserData()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this, "Account creation failed", Toast.LENGTH_SHORT).show()
                Log.d("Account", "createAccount: Failure", task.exception)
            }
        }
    }
// saving data into database
    private fun saveUserData() {

        userName = binding.nameOfOwner.text.toString().trim()
        nameOfResturant = binding.nameOfRestaurant.text.toString().trim()
        email = binding.emailorPhoneNumber.text.toString().trim()
        password = binding.password1.text.toString().trim()

        val user = UserModel(userName,nameOfResturant,email,password)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid

    //save user data into Firebase database
        database.child("user").child(userId).setValue(user)



    }
}