package com.example.adminwaveoffood.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.example.adminwaveoffood.databinding.ActivityItemItemBinding
import com.example.adminwaveoffood.model.AllMenu
import com.google.firebase.database.DatabaseReference

class MenuitemAdapter(
    private val context: Context,
    private val menuList: ArrayList<AllMenu>,
    databaseReference: DatabaseReference

) : RecyclerView.Adapter<MenuitemAdapter.AddItemViewHolder>() {
    private val itemQuantities = IntArray(menuList.size) { 1 }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        val binding =
            ActivityItemItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddItemViewHolder(binding)
    }


    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
        holder.bind(position)
    }


    override fun getItemCount(): Int = menuList.size


    inner class AddItemViewHolder(private val binding: ActivityItemItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantities[position]
                val menuItem = menuList[position]
                val uriString = menuItem.foodImage
                val uri = Uri.parse(uriString)
                foodNameTextView.text = menuItem.foodName
                foodNamePrice.text = menuItem.foodPrice
                Glide.with(context).load(uri).into(foodImageView)
                Log.d("GlideImageLoader", "Loading image from: $uri")
                quantityTextView.text = quantity.toString()


                minusButton.setOnClickListener {
                    decreaseQuantity()
                }
                plussButton.setOnClickListener {
                    increaseQuantity()
                }

                deleteButton.setOnClickListener {
                    deleteQuantity()
                }

            }

        }


        private fun deleteQuantity() {
            menuList.removeAt(position)
            menuList.removeAt(position)
            menuList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, menuList.size)
        }

        private fun increaseQuantity() {
            if (itemQuantities[position] < 10) {
                itemQuantities[position]++
                binding.quantityTextView.text = itemQuantities[position].toString()
            }
        }

        private fun decreaseQuantity() {
            if (itemQuantities[position] > 1) {
                itemQuantities[position]--
                binding.quantityTextView.text = itemQuantities[position].toString()
            }
        }


    }

}