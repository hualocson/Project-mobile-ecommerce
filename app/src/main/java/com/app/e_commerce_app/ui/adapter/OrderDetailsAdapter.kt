package com.app.e_commerce_app.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.e_commerce_app.common.BindableAdapter
import com.app.e_commerce_app.databinding.ItemNewsBinding
import com.app.e_commerce_app.databinding.ItemOrderDetailsBinding
import com.app.e_commerce_app.databinding.ItemOrdersBinding
import com.app.e_commerce_app.model.CartEntity
import com.app.e_commerce_app.model.SaleJson
import com.app.e_commerce_app.model.order.OrderJson
import com.app.e_commerce_app.model.order.OrderLineJson

class OrderDetailsAdapter(
    private val context: Context,
    private val onClick: (OrderLineJson) -> Unit,
) : RecyclerView.Adapter<OrderDetailsAdapter.NewViewHolder>(), BindableAdapter<OrderLineJson> {
    private var items: ArrayList<OrderLineJson> = ArrayList()

    inner class NewViewHolder(private val binding: ItemOrderDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(orderLineJson: OrderLineJson) {
            binding.orderlineitem = orderLineJson
            binding.executePendingBindings()
            binding.textView.setOnClickListener { onClick(orderLineJson) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewViewHolder {
        val binding =
            ItemOrderDetailsBinding.inflate(LayoutInflater.from(context), parent, false)
        return NewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewViewHolder, position: Int) {
        holder.bindData(items[position])
    }

    override fun getItemCount(): Int = items.size

    override fun setItems(items: List<OrderLineJson>) {
        this.items = items as ArrayList<OrderLineJson>
        notifyDataSetChanged()
    }
}