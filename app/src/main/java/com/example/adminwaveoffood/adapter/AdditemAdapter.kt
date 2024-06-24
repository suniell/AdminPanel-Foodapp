package com.example.adminwaveoffood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminwaveoffood.databinding.ActivityItemItemBinding

class AdditemAdapter(private val MenuItemName : ArrayList<String>, private val MenuItemPrice : ArrayList<String>, private val MenuItemImage : ArrayList<Int> ) :RecyclerView.Adapter<AdditemAdapter.AddItemViewHolder>() {
private val itemQuantities = IntArray(MenuItemName.size){1}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        val binding = ActivityItemItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AddItemViewHolder(binding)
    }



    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
        holder.bind(position)
    }



    override fun getItemCount(): Int = MenuItemName.size


    inner class AddItemViewHolder(private val binding : ActivityItemItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                foodNameTextView.text = MenuItemName[position]
                foodNamePrice.text = MenuItemPrice[position]
                foodImageView.setImageResource(MenuItemImage[position])


                minusButton.setOnClickListener {
                    decreaseQuantity()
                }
                plusButton.setOnClickListener {
                    increaseQuantity()
                }

                deleteButton.setOnClickListener {
                    deleteQuantity()
                }

            }

        }


        private fun deleteQuantity() {
                MenuItemName.removeAt(position)
                MenuItemPrice.removeAt(position)
                MenuItemImage.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position,MenuItemName.size)
        }

        private fun increaseQuantity() {
            if (itemQuantities[position]<10){
                itemQuantities[position]++
                binding.quantityTextView.text = itemQuantities[position].toString()
            }
        }

        private fun decreaseQuantity() {
            if (itemQuantities[position]<1){
                itemQuantities[position]--
                binding.quantityTextView.text = itemQuantities[position].toString()
            }
        }


    }

}