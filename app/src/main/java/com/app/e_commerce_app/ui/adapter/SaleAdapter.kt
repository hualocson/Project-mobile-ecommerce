package com.app.e_commerce_app.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.e_commerce_app.common.BindableAdapter
import com.app.e_commerce_app.databinding.ItemNewsBinding
import com.app.e_commerce_app.model.SaleJson

class SaleAdapter(
    private val context: Context,
    private val onClick: (SaleJson) -> Unit,
) : RecyclerView.Adapter<SaleAdapter.NewViewHolder>(), BindableAdapter<SaleJson> {
    private var items: ArrayList<SaleJson> = ArrayList()

    inner class NewViewHolder(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(saleJson: SaleJson) {
            binding.newData = saleJson
            binding.executePendingBindings()
            binding.tvTitle.setOnClickListener { onClick(saleJson)}
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

    override fun setItems(items: List<SaleJson>) {
        this.items = items as ArrayList<SaleJson>
        notifyDataSetChanged()
    }
}