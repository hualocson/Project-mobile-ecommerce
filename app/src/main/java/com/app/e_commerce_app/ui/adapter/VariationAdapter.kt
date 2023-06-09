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
import com.app.e_commerce_app.utils.OnVariationOptionClick

class VariationAdapter(
    private val context: Context,
    private val onClick: (VariationModel) -> Unit,
    private val onVariationOptionClick: OnVariationOptionClick
) : RecyclerView.Adapter<VariationAdapter.VariationViewHolder>(), BindableAdapter<VariationModel> {

    private var items: ArrayList<VariationModel> = ArrayList()
    private lateinit var adapter: VariationOptionAdapter

    inner class VariationViewHolder(private val binding: ItemProductItemVariationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(variationModel: VariationModel) {
            binding.variation = variationModel
            binding.executePendingBindings()
            adapter = VariationOptionAdapter(context, variationModel.variationOptions, onVariationOptionClick)
            binding.rvOptions.adapter = adapter
            binding.rvOptions.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            binding.rvOptions.setOnClickListener {
                onClick(variationModel)
            }
        }
    }

    fun setActive (productItemId : Int) {
        adapter.setActive(productItemId)
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
        notifyDataSetChanged()
    }
}