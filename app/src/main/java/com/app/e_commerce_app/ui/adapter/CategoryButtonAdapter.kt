package com.app.e_commerce_app.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.app.e_commerce_app.databinding.ItemCategoryButtonBinding
import com.app.e_commerce_app.model.CategoryModel
import com.app.e_commerce_app.model.CategoryRadioButton

class CategoryButtonAdapter(
    private val context: Context,
    private val onClick: (CategoryRadioButton) -> Unit
) : RecyclerView.Adapter<CategoryButtonAdapter.CategoryViewHolder>() {

    private val categoryList: ArrayList<CategoryRadioButton> = ArrayList()
    private var isNewRadioButtonChecked = false;
    private var lastCheckedPosition = -1;


    inner class CategoryViewHolder(private val binding: ItemCategoryButtonBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bindData(categoryModel: CategoryRadioButton, position: Int) {
            binding.btnCategory.text = categoryModel.categoryName

            if(isNewRadioButtonChecked) {
                binding.btnCategory.isChecked = categoryModel.isChecked
            }
            else {
                if(position == 0) {
                    binding.btnCategory.isChecked = true
                    lastCheckedPosition = 0
                }
            }
            binding.btnCategory.setOnClickListener {
                handleRadioButtonCheck(adapterPosition)
                onClick(categoryModel)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryButtonBinding.inflate(LayoutInflater.from(context), parent, false)

        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        with(holder) {
            this.bindData(categoryList[position], position)
        }
    }

    fun setCategories(categories: ArrayList<CategoryModel>) {
        val firstData = CategoryRadioButton(0, "All", true)
        categoryList.add(firstData)
        categories.forEach {
            categoryList.add(it.toCategoryRadio())
        }
        notifyDataSetChanged()
    }

    private fun handleRadioButtonCheck(position: Int) {
        isNewRadioButtonChecked = true
        categoryList[lastCheckedPosition].isChecked = false
        categoryList[position].isChecked = true
        lastCheckedPosition = position
        notifyDataSetChanged()
    }
}