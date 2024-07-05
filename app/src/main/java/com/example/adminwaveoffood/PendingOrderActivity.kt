package com.example.adminwaveoffood

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.adminwaveoffood.adapter.PendingOrderAdapter
import com.example.adminwaveoffood.databinding.ActivityPendingOrderBinding

class PendingOrderActivity : AppCompatActivity() {
    private val binding: ActivityPendingOrderBinding by lazy {
        ActivityPendingOrderBinding.inflate(layoutInflater)
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


        val orderCustomerName = arrayListOf(
            "Rocky Bhai ",
            "baggu Don",
            "Lallu Patakha"


        )

        val orderQuantity = arrayListOf(
            "8",
            "6",
            "5",
        )

        val orderFoodImage = arrayListOf(
            R.drawable.menu3,
            R.drawable.menu4,
            R.drawable.menu2,
        )

        val adapter = PendingOrderAdapter(orderCustomerName, orderQuantity, orderFoodImage,this)
        binding.pendingOrderRecyclerView.adapter = adapter
        binding.pendingOrderRecyclerView.layoutManager = LinearLayoutManager(this)



        binding.backButton.setOnClickListener {
            finish()
        }


    }
}