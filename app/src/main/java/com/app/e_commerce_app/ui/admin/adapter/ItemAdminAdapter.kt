package com.app.e_commerce_app.ui.admin.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.e_commerce_app.common.BindableAdapter
import com.app.e_commerce_app.databinding.AdminItemBinding
import com.app.e_commerce_app.databinding.AdminItemShippingBinding
import com.app.e_commerce_app.databinding.ItemChooseBinding
import com.app.e_commerce_app.model.ChooseItem

class ItemAdminAdapter(
    private val context: Context,
    private val onClick: (ChooseItem) -> Unit
) : RecyclerView.Adapter<ItemAdminAdapter.ItemAdminViewHolder>(),
    BindableAdapter<ChooseItem> {

    private var items: ArrayList<ChooseItem> = ArrayList()

    inner class ItemAdminViewHolder(private val binding: AdminItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bindData(item: ChooseItem) {
            binding.item = item
            binding.executePendingBindings()

            binding.btnUpdate.setOnClickListener {
                onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdminViewHolder {
        val binding = AdminItemBinding.inflate(LayoutInflater.from(context), parent, false)

        return ItemAdminViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ItemAdminViewHolder, position: Int) {
        holder.bindData(items[position])
    }

    override fun setItems(items: List<ChooseItem>) {
        Log.d("LOADADMIN", items.toString())
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}