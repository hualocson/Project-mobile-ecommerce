package com.app.e_commerce_app.ui.admin.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.e_commerce_app.common.BindableAdapter
import com.app.e_commerce_app.databinding.AdminItemShippingBinding
import com.app.e_commerce_app.model.ShippingJson

class ShippingAdminAdapter(
    private val context: Context,
    private val onClick: (ShippingJson) -> Unit
) : RecyclerView.Adapter<ShippingAdminAdapter.ShippingAdminViewHolder>(),
    BindableAdapter<ShippingJson> {

    private var items: ArrayList<ShippingJson> = ArrayList()

    inner class ShippingAdminViewHolder(private val binding: AdminItemShippingBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bindData(shippingJson: ShippingJson) {
            binding.item = shippingJson
            binding.executePendingBindings()

            binding.Layout.setOnClickListener {
                onClick(shippingJson)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShippingAdminViewHolder {
        val binding = AdminItemShippingBinding.inflate(LayoutInflater.from(context), parent, false)

        return ShippingAdminViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ShippingAdminViewHolder, position: Int) {
        holder.bindData(items[position])
    }

    override fun setItems(items: List<ShippingJson>) {
        Log.d("LOADADMIN", items.toString())
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}