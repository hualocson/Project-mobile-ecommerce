package com.app.e_commerce_app.ui.admin.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import androidx.recyclerview.widget.RecyclerView
import com.app.e_commerce_app.R
import com.app.e_commerce_app.common.BindableAdapter
import com.app.e_commerce_app.databinding.ItemAdminVariationBinding
import com.app.e_commerce_app.model.variation.VariationModel
import com.app.e_commerce_app.model.variation.VariationOptionModel
import com.app.e_commerce_app.ui.admin.adapter.adapterInterface.DropDownItemSelectedListener

class VariationAdminAdapter(
    private val context: Context,
) : RecyclerView.Adapter<VariationAdminAdapter.VariationAdminViewHolder>(),
    BindableAdapter<VariationModel> {

    private var items: List<VariationModel> = listOf()
    var dropdownItemSelectedListener: DropDownItemSelectedListener? = null
    private var activeItems: List<Pair<Int, String>> = listOf()

    inner class VariationAdminViewHolder(
        private val binding: ItemAdminVariationBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private val autoCompleteTextView = binding.dropdownMenu.editText as AutoCompleteTextView
        private val adapter =
            CustomDropdownAdapter(binding.root.context, R.layout.item_custom_dropdown)

        fun bindData(variation: VariationModel) {
            binding.variation = variation
            binding.executePendingBindings()
            val data = variation.variationOptions.map {
                Pair(it.id, it.value)
            }
            adapter.setItems(data)
            autoCompleteTextView.setAdapter(adapter)
            autoCompleteTextView.setOnItemClickListener { parent, _, position, _ ->
                val selectedPair = parent.getItemAtPosition(position) as? Pair<*, *>
                selectedPair?.let {
                    dropdownItemSelectedListener?.onItemSelected(
                        VariationOptionModel(
                            it.first as Int,
                            it.second as String,
                            variation.id
                        )
                    )
                    val selectedString: String = it.second as String
                    autoCompleteTextView.setText(selectedString, false)
                }
            }
        }

        fun changeActive(item: Pair<Int, String>) {
            Log.d("DETE", item.toString())
            Log.d("DETEADAP", adapter.toString())
            if (item.first != 0) {
                val pos = adapter.getPosition(item)
                if (pos != -1)
                    autoCompleteTextView.setText(item.second, false)
            }
        }
    }

    fun setActiveItem(items: List<Pair<Int, String>>) {
        activeItems = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VariationAdminViewHolder {
        val binding =
            ItemAdminVariationBinding.inflate(LayoutInflater.from(context), parent, false)

        return VariationAdminViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VariationAdminViewHolder, position: Int) {
        holder.bindData(items[position])
        if (activeItems.isNotEmpty())
            holder.changeActive(activeItems[position])
    }

    override fun setItems(items: List<VariationModel>) {
        val sorted = items.sortedBy { it.id }
        this.items = sorted
        notifyDataSetChanged()
    }
}