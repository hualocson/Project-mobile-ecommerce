package com.app.e_commerce_app.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.e_commerce_app.common.BindableAdapter
import com.app.e_commerce_app.databinding.ItemProductBinding
import com.app.e_commerce_app.model.product.ProductModel
import com.squareup.picasso.Picasso

class ProductAdapter(
    private val context: Context,
    private val onClick : (ProductModel) -> Unit,
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(), BindableAdapter<ProductModel> {

    private var productList: List<ProductModel> = listOf()
    inner class ProductViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(productModel: ProductModel) {
            binding.productData = productModel
            binding.executePendingBindings()

//            binding.tvProductsName.text = productModel.name
//            binding.productPrice.text = productModel.minPrice.toString()
//            Picasso.get().load(productModel.productImage).into(binding.productImg)
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
        with(holder)  {
            bindData(productList[position])
        }
    }

    fun setProducts(products : List<ProductModel> ) {
        this.productList = products
        notifyDataSetChanged()
    }

    override fun setItems(items: List<ProductModel>) {
        this.productList = items
        notifyDataSetChanged()
    }
}