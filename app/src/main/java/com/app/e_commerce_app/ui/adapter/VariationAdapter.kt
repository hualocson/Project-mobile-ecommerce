package com.app.e_commerce_app.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.e_commerce_app.common.BindableAdapter
import com.app.e_commerce_app.databinding.ItemProductItemVariationBinding
import com.app.e_commerce_app.model.variation.VariationModel
import com.app.e_commerce_app.model.variation.VariationOptionModel

class VariationAdapter(
    private val context: Context,
    private val onClick: (VariationModel) -> Unit,
) : RecyclerView.Adapter<VariationAdapter.VariationViewHolder>(), BindableAdapter<VariationModel> {

    private var items: ArrayList<VariationModel> = ArrayList()

    inner class VariationViewHolder(private val binding: ItemProductItemVariationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(variationModel: VariationModel) {
            binding.variation = variationModel
            binding.executePendingBindings()
            binding.tvProductItemVariationName.setOnClickListener { onClick(variationModel) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VariationViewHolder {
        val binding =
            ItemProductItemVariationBinding.inflate(LayoutInflater.from(context), parent, false)
        return VariationViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VariationViewHolder, position: Int) {
        holder.bindData(items[position])
    }

    override fun setItems(items: List<VariationModel>) {
        this.items = items as ArrayList<VariationModel>
        Log.d("LogItems", items.toString())
        notifyDataSetChanged()
    }
}