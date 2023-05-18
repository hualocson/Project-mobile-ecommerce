package com.app.e_commerce_app.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.e_commerce_app.common.BindableAdapter
import com.app.e_commerce_app.databinding.ItemCategoryButtonBinding
import com.app.e_commerce_app.model.CategoryRadioButton

class CategoryButtonAdapter(
    private val context: Context,
    private val onClick: (CategoryRadioButton) -> Unit,
) : RecyclerView.Adapter<CategoryButtonAdapter.CategoryViewHolder>(),
    BindableAdapter<CategoryRadioButton> {

    private var items: ArrayList<CategoryRadioButton> = ArrayList()
    private var lastCheckedPosition: Int = 0

    inner class CategoryViewHolder(private val binding: ItemCategoryButtonBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bindData(categoryRadioButton: CategoryRadioButton, position: Int) {
            binding.categoryItem = categoryRadioButton
            binding.executePendingBindings()

            binding.btnCategory.setOnClickListener {
                handleRadioButtonCheck(position)
                onClick(categoryRadioButton)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryButtonBinding.inflate(LayoutInflater.from(context), parent, false)

        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bindData(items[position], position)
    }

//    fun setCategories(categories: List<CategoryModel>, categoryId: Int = 0) {
//        val first = CategoryRadioButton(0, "All")
//
//        categoryList.add(first)
//
//
//        var i = 0
//        var pos = -1
//        categories.forEach {
//            if (it.id == categoryId) {
//                pos = i + 1
//            }
//            categoryList.add(it.toCategoryRadio())
//            i++
//        }
//
//        if (pos > 0) {
//            isNewRadioButtonChecked = true
//            categoryList[pos].isChecked = true
//
//            val temp = categoryList[pos]
//            categoryList.removeAt(pos)
//            categoryList.add(1, temp)
//            lastCheckedPosition = 1
//        }
//
//        notifyDataSetChanged()
//    }

    private fun handleRadioButtonCheck(position: Int) {
        items[lastCheckedPosition].isChecked = false
        items[position].isChecked = true
        lastCheckedPosition = position
        notifyDataSetChanged()
    }

    override fun setItems(items: List<CategoryRadioButton>) {
        this.items.clear()
        this.items.add(CategoryRadioButton(0, "All", true))
        items.forEach {
            this.items.add(it)
        }
        notifyDataSetChanged()
    }
}