package com.app.e_commerce_app.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.e_commerce_app.databinding.ItemProductItemVariationValueBinding
import com.app.e_commerce_app.model.variation.VariationOptionModel

class VariationOptionAdapter(
    private val context: Context,
    private val items: List<VariationOptionModel>,
    private var onClick: (VariationOptionModel) -> Unit,
) : RecyclerView.Adapter<VariationOptionAdapter.VariationOptionViewHolder>() {

    private var lastCheckedPosition: Int = -1

    inner class VariationOptionViewHolder(private val binding: ItemProductItemVariationValueBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(variationOption: VariationOptionModel, position: Int) {
            binding.variationOption = variationOption
            binding.executePendingBindings()
            binding.btnVariationOption.setOnClickListener {
                handleRadioButtonCheck(position)
                onClick(variationOption)
            }
        }
    }

    fun setActive(productItemId: Int) {
        items.map {
            it.isChecked = it.id == productItemId
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VariationOptionViewHolder {
        val binding = ItemProductItemVariationValueBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        return VariationOptionViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VariationOptionViewHolder, position: Int) {
        holder.bindData(items[position], position)
    }

    private fun handleRadioButtonCheck(position: Int) {
        if (lastCheckedPosition != -1)
            items[lastCheckedPosition].isChecked = false
        items[position].isChecked = true
        lastCheckedPosition = position
        notifyDataSetChanged()
    }
}