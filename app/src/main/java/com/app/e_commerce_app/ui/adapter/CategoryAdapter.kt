package com.app.e_commerce_app.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.e_commerce_app.common.BindableAdapter
import com.app.e_commerce_app.databinding.ItemCategoryBinding
import com.app.e_commerce_app.model.CategoryModel
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.squareup.picasso.Picasso

class CategoryAdapter(
   private val context: Context,
   private val onClick : (CategoryModel) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(), BindableAdapter<CategoryModel> {

    private var categoryList: List<CategoryModel> = listOf()
    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(categoryModel: CategoryModel) {
            binding.tvCategoryName.text = categoryModel.categoryName
//            Glide.with(binding.categoryIc.context).load(categoryModel.categoryIc).into(binding.categoryIc)

            Picasso.get().load(categoryModel.categoryIc).into(binding.categoryIc)
            binding.layoutCategoryItem.setOnClickListener { onClick(categoryModel) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(context), parent, false)

        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        with(holder)  {
            this.bindData(categoryList[position])
        }
    }

    override fun setItems(items: List<CategoryModel>) {
        categoryList = items
        Log.d("SetItem icon adapter ", "")
        notifyDataSetChanged()
    }
}