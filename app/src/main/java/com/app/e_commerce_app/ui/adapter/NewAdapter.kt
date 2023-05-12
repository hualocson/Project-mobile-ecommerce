package com.app.e_commerce_app.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.e_commerce_app.common.BindableAdapter
import com.app.e_commerce_app.databinding.ItemAddressBinding
import com.app.e_commerce_app.databinding.ItemNewsBinding
import com.app.e_commerce_app.databinding.ItemProductItemVariationBinding
import com.app.e_commerce_app.model.AddressJson
import com.app.e_commerce_app.model.NewJson
import com.app.e_commerce_app.model.variation.VariationModel

class NewAdapter(
    private val context: Context,
    private val onClick: (NewJson) -> Unit,
) : RecyclerView.Adapter<NewAdapter.NewViewHolder>(), BindableAdapter<NewJson> {
    private var items: ArrayList<NewJson> = ArrayList()

    inner class NewViewHolder(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(newJson: NewJson) {
            binding.newData = newJson
            binding.executePendingBindings()
            binding.Layout.setOnClickListener { onClick(newJson)}
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewViewHolder {
        val binding =
            ItemNewsBinding.inflate(LayoutInflater.from(context), parent, false)
        return NewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewViewHolder, position: Int) {
        holder.bindData(items[position])
    }

    override fun getItemCount(): Int = items.size

    override fun setItems(items: List<NewJson>) {
        this.items = items as ArrayList<NewJson>
        notifyDataSetChanged()
    }
}