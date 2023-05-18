package com.app.e_commerce_app.ui.admin.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.e_commerce_app.common.BindableAdapter
import com.app.e_commerce_app.databinding.ItemAdminVariationBinding

class VariationAdminAdapter(
    private val context: Context,
    private val onEditClick: (String) -> Unit,
    private val onRemoveClick: (String) -> Unit,
) : RecyclerView.Adapter<VariationAdminAdapter.VariationAdminViewHolder>(),
    BindableAdapter<String> {

    private var items: List<String> = listOf()

    inner class VariationAdminViewHolder(private val binding: ItemAdminVariationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(variation: String) {
            binding.item = variation
            binding.executePendingBindings()
            binding.btnUpdate.setOnClickListener {
                onEditClick(variation)
            }
            binding.btnDelete.setOnClickListener {
                onRemoveClick(variation)
                val pos = items.indexOf(variation)
                items.minus(variation)
                notifyItemRemoved(pos)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VariationAdminViewHolder {
        val binding = ItemAdminVariationBinding.inflate(LayoutInflater.from(context), parent, false)

        return VariationAdminViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VariationAdminViewHolder, position: Int) {
        holder.bindData(items[position])
    }

    override fun setItems(items: List<String>) {
        this.items = items
        notifyDataSetChanged()
    }
}