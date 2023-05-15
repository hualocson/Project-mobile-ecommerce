package com.app.e_commerce_app.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.e_commerce_app.common.BindableAdapter
import com.app.e_commerce_app.databinding.ItemNewsBinding
import com.app.e_commerce_app.databinding.ItemOrdersBinding
import com.app.e_commerce_app.model.CartEntity
import com.app.e_commerce_app.model.SaleJson
import com.app.e_commerce_app.model.order.OrderJson

class OrderAdapter(
    private val context: Context,
    private val onClick: (OrderJson) -> Unit,
) : RecyclerView.Adapter<OrderAdapter.NewViewHolder>(), BindableAdapter<OrderJson> {
    private var items: ArrayList<OrderJson> = ArrayList()

    inner class NewViewHolder(private val binding: ItemOrdersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(orderJson: OrderJson) {
            binding.orderitem = orderJson
            binding.executePendingBindings()
            binding.btnEvent.setOnClickListener { onClick(orderJson) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewViewHolder {
        val binding =
            ItemOrdersBinding.inflate(LayoutInflater.from(context), parent, false)
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