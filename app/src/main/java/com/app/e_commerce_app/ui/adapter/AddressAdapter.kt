package com.app.e_commerce_app.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.e_commerce_app.common.BindableAdapter
import com.app.e_commerce_app.databinding.ItemAddressBinding
import com.app.e_commerce_app.databinding.ItemProductItemVariationBinding
import com.app.e_commerce_app.model.AddressJson
import com.app.e_commerce_app.model.variation.VariationModel

class AddressAdapter(
    private val context: Context,
    private val onClick: (AddressJson) -> Unit,
) : RecyclerView.Adapter<AddressAdapter.AddressViewHolder>(), BindableAdapter<AddressJson> {
    private var items: ArrayList<AddressJson> = ArrayList()

    inner class AddressViewHolder(private val binding: ItemAddressBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(addressJson: AddressJson) {
            binding.addressData = addressJson
            binding.executePendingBindings()
            binding.btnUpdate.setOnClickListener { onClick(addressJson) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddressViewHolder {
        val binding =
            ItemAddressBinding.inflate(LayoutInflater.from(context), parent, false)
        return AddressViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.bindData(items[position])
    }

    override fun getItemCount(): Int = items.size

    override fun setItems(items: List<AddressJson>) {
        this.items = items as ArrayList<AddressJson>
        notifyDataSetChanged()
    }
}