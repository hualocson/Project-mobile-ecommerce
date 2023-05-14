package com.app.e_commerce_app.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.e_commerce_app.common.BindableAdapter
import com.app.e_commerce_app.databinding.ItemCartBinding
import com.app.e_commerce_app.model.CartEntity
import com.app.e_commerce_app.model.toCartItemModel

class CartAdapter(
    private val context: Context,
    private val onClick: ((CartEntity) -> Unit)? = null,
    private val onDelete: ((CartEntity) -> Unit)? = null,
    private val itemClickCallback: ((CartEntity) -> Unit)? = null
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>(), BindableAdapter<CartEntity> {

    private var carts: List<CartEntity> = listOf()
    var isInCheckout = false

    inner class CartViewHolder(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(cartEntity: CartEntity) {
//            binding.textView.text = cartModel.name
//            Picasso.get().load(cartModel.img).into(binding.shapeableImageView)
//            binding.tvPricePopular.text = cartModel.price
            binding.cartitem = cartEntity.toCartItemModel()
            binding.executePendingBindings()
            var itemQty: Int = cartEntity.quantity.toInt()

            if (isInCheckout) {
                binding.btnDeleteItemCart.visibility = View.GONE
                binding.btnMinusQuantity.visibility = View.INVISIBLE
                binding.btnPlusQuantity.visibility = View.GONE
                binding.tvQuantity.isEnabled = false
            } else {
                binding.btnPlusQuantity.setOnClickListener {
                    itemQty = itemQty + 1
                    binding.tvQuantity.text = itemQty.toString()
                    cartEntity.quantity = itemQty
                    itemClickCallback?.let { it(cartEntity) }
                }
                binding.btnMinusQuantity.setOnClickListener {
                    if (itemQty != 0) {
                        itemQty = itemQty - 1
                        if (itemQty == 0) {
                            onDelete?.let { it(cartEntity) }
                        }
                        binding.tvQuantity.text = itemQty.toString()
                        cartEntity.quantity = itemQty
                        itemClickCallback?.let { it(cartEntity) }
                    }
                }
                binding.btnDeleteItemCart.setOnClickListener {
                    onDelete?.let { it(cartEntity) }
                }
                binding.Layout.setOnClickListener { onClick?.let { it(cartEntity) } }

            }
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
        with(holder) {
            this.bindData(carts[position])
        }
    }

    override fun setItems(items: List<CartEntity>) {
        this.carts = items
        Log.d("items", items.toString())
        notifyDataSetChanged()
    }
}