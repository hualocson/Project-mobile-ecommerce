package com.app.e_commerce_app.ui.admin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.e_commerce_app.common.BindableAdapter
import com.app.e_commerce_app.databinding.AdminItemVaritionBinding
import com.app.e_commerce_app.databinding.ItemNewsBinding
import com.app.e_commerce_app.databinding.ItemSalesAdminBinding
import com.app.e_commerce_app.model.CategoryModel
import com.app.e_commerce_app.model.SaleJson
import com.app.e_commerce_app.model.product.ProductItemJson
import com.app.e_commerce_app.model.variation.VariationGetAllModel

class VaritionAllAdminAdapter(
    private val context: Context,
    private val onClick: (CategoryModel) -> Unit,
) : RecyclerView.Adapter<VaritionAllAdminAdapter.NewViewHolder>(), BindableAdapter<CategoryModel> {
    private var items: ArrayList<CategoryModel> = ArrayList()
    private var varitionsList: List<VariationGetAllModel> = listOf()

    inner class NewViewHolder(private val binding: AdminItemVaritionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(categoryModel: CategoryModel) {
            binding.item = categoryModel
            binding.executePendingBindings()
            var desc_varitions: String = String()
            varitionsList.forEach {
                if(it.categoryId == categoryModel.id){
                    desc_varitions += it.name + " | "
                }
            }
            binding.textVartion.text = desc_varitions
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewViewHolder {
        val binding =
            AdminItemVaritionBinding.inflate(LayoutInflater.from(context), parent, false)
        return NewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewViewHolder, position: Int) {
        holder.bindData(items[position])
    }

    override fun getItemCount(): Int = items.size

    override fun setItems(items: List<CategoryModel>) {
        this.items = items as ArrayList<CategoryModel>
        notifyDataSetChanged()
    }

    fun setVaritionsData(varitionsList: List<VariationGetAllModel>) {
        this.varitionsList = varitionsList
        notifyDataSetChanged()
    }
}