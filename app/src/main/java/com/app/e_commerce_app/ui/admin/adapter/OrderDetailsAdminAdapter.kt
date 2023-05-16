package com.app.e_commerce_app.ui.admin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.app.e_commerce_app.common.BindableAdapter
import com.app.e_commerce_app.data.repository.ProductRepository
import com.app.e_commerce_app.databinding.ItemNewsBinding
import com.app.e_commerce_app.databinding.ItemOrderDetailsAdminBinding
import com.app.e_commerce_app.databinding.ItemOrderDetailsBinding
import com.app.e_commerce_app.databinding.ItemOrdersBinding
import com.app.e_commerce_app.model.CartEntity
import com.app.e_commerce_app.model.SaleJson
import com.app.e_commerce_app.model.order.OrderJson
import com.app.e_commerce_app.model.order.OrderLineJson
import com.app.e_commerce_app.model.product.ProductItemJson
import com.app.e_commerce_app.viewmodel.ProductViewModel
import com.app.e_commerce_app.viewmodel.SearchViewModel

class OrderDetailsAdminAdapter(
    private val context: Context,
    private val onClick: (OrderLineJson) -> Unit,
) : RecyclerView.Adapter<OrderDetailsAdminAdapter.NewViewHolder>(), BindableAdapter<OrderLineJson> {
    private var items: ArrayList<OrderLineJson> = ArrayList()
    private var myDataList: List<ProductItemJson> = listOf()
    inner class NewViewHolder(private val binding: ItemOrderDetailsAdminBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(orderLineJson: OrderLineJson) {
            binding.orderlineitem = orderLineJson
            var desc: String = String()
            myDataList.forEach {
                if(it.id == orderLineJson.productItemId){
                    it.productConfigurations.forEach {
                        desc = desc + it.value + " "
                    }
                }
            }
            binding.tvDesc.text = desc
            binding.executePendingBindings()
            binding.textView.setOnClickListener { onClick(orderLineJson) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewViewHolder {
        val binding =
            ItemOrderDetailsAdminBinding.inflate(LayoutInflater.from(context), parent, false)
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

    fun setData(myDataList: List<ProductItemJson>) {
        this.myDataList = myDataList
        notifyDataSetChanged()
    }


}