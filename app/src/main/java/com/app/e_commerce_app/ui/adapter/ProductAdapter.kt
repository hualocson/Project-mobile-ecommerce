package com.app.e_commerce_app.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.e_commerce_app.common.BindableAdapter
import com.app.e_commerce_app.databinding.ItemProductBinding
import com.app.e_commerce_app.model.product.ProductModel

class ProductAdapter(
    private val context: Context,
    private val onClick: (ProductModel) -> Unit,
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(), BindableAdapter<ProductModel> {

    private var productList: ArrayList<ProductModel> = ArrayList()

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(productModel: ProductModel) {
            binding.productData = productModel
            binding.executePendingBindings()
            binding.layoutProductsItem.setOnClickListener { onClick(productModel) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(context), parent, false)

        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bindData(productList[position])
    }

    override fun setItems(items: List<ProductModel>) {
        Log.d("SetItems Product", items.toString())
        this.productList = items as ArrayList<ProductModel>
        notifyDataSetChanged()
    }
    fun setFilterList(items: List<ProductModel>){
        this.productList = items as ArrayList<ProductModel>
        notifyDataSetChanged()
    }
}