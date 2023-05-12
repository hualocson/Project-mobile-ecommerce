package com.app.e_commerce_app.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.e_commerce_app.common.BindableAdapter
import com.app.e_commerce_app.databinding.ItemCategoryButtonBinding
import com.app.e_commerce_app.databinding.ItemChooseBinding
import com.app.e_commerce_app.model.CategoryRadioButton
import com.app.e_commerce_app.model.ChooseItem

class ChooseAdapter(
    private val context: Context,
    private val onClick: (ChooseItem) -> Unit,
) : RecyclerView.Adapter<ChooseAdapter.ChooseViewHolder>(),
    BindableAdapter<ChooseItem> {

    private var items: ArrayList<ChooseItem> = ArrayList()
    private var lastCheckedPosition: Int = 0

    inner class ChooseViewHolder(private val binding: ItemChooseBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bindData(item: ChooseItem, position: Int) {
            binding.item = item
            Log.d("Item in Adapter", item.toString())
            binding.executePendingBindings()

            binding.radioButton.setOnClickListener {
                handleRadioButtonCheck(position)
                onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseViewHolder {
        val binding = ItemChooseBinding.inflate(LayoutInflater.from(context), parent, false)

        return ChooseViewHolder(binding)
    }

    fun getActiveItem () : ChooseItem {
        return items.find { it.isChecked }!!
    }
    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ChooseViewHolder, position: Int) {
        holder.bindData(items[position], position)
    }

    private fun handleRadioButtonCheck(position: Int) {
        items[lastCheckedPosition].isChecked = false
        items[position].isChecked = true
        lastCheckedPosition = position
        notifyDataSetChanged()
    }
    override fun setItems(items: List<ChooseItem>) {
        this.items.clear()
        items[0].isChecked = true
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}