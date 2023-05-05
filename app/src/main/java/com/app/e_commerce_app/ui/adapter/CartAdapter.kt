package com.app.e_commerce_app.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Delete
import com.app.e_commerce_app.common.BindableAdapter
import com.app.e_commerce_app.databinding.ItemCartBinding
import com.app.e_commerce_app.databinding.ItemCategoryBinding
import com.app.e_commerce_app.model.CartModel
import com.app.e_commerce_app.model.CategoryModel
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.squareup.picasso.Picasso

class CartAdapter(
   private val context: Context,
   private val onClick : (CartModel) -> Unit,
   private val onDelete: (CartModel) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() , BindableAdapter<CartModel> {

    private var carts: List<CartModel> = listOf()
    inner class CartViewHolder(private val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(cartModel: CartModel) {
//            binding.textView.text = cartModel.name
//            Picasso.get().load(cartModel.img).into(binding.shapeableImageView)
//            binding.tvPricePopular.text = cartModel.price
            binding.cartitem = cartModel
            binding.executePendingBindings()
            binding.btnDeleteItemCart.setOnClickListener { onDelete(cartModel)}
            binding.Layout.setOnClickListener { onClick(cartModel)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(context), parent, false)

        return CartViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return carts.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        with(holder)  {
            this.bindData(carts[position])
        }
    }


    override fun setItems(items: List<CartModel>) {
        this.carts = items
        notifyDataSetChanged()
    }
}