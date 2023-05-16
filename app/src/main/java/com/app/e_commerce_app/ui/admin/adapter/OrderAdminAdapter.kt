package com.app.e_commerce_app.ui.admin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.e_commerce_app.common.BindableAdapter
import com.app.e_commerce_app.databinding.AdminItemOrdersBinding
import com.app.e_commerce_app.model.order.OrderJson

class OrderAdminAdapter(
    private val context: Context,
    private val onClick: (OrderJson) -> Unit,
) : RecyclerView.Adapter<OrderAdminAdapter.NewViewHolder>(), BindableAdapter<OrderJson> {
    private var items: ArrayList<OrderJson> = ArrayList()

    inner class NewViewHolder(private val binding: AdminItemOrdersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(orderJson: OrderJson) {
            binding.orderitem = orderJson
            binding.executePendingBindings()
            binding.btnUpdate.setOnClickListener { onClick(orderJson) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewViewHolder {
        val binding =
            AdminItemOrdersBinding.inflate(LayoutInflater.from(context), parent, false)
        return NewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewViewHolder, position: Int) {
        holder.bindData(items[position])
    }

    override fun getItemCount(): Int = items.size

    override fun setItems(items: List<OrderJson>) {
        this.items = items as ArrayList<OrderJson>
        notifyDataSetChanged()
    }
}