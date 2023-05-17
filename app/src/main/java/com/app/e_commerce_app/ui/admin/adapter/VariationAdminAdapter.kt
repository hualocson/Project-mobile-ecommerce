package com.app.e_commerce_app.ui.admin.adapter

import android.content.Context
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
    private val onClick: (VariationModel) -> Unit,
) : RecyclerView.Adapter<VariationAdminAdapter.VariationAdminViewHolder>(),
    BindableAdapter<VariationModel> {

    private var items: ArrayList<VariationModel> = ArrayList()
    var dropdownItemSelectedListener: DropDownItemSelectedListener? = null

    inner class VariationAdminViewHolder(
        private val binding: ItemAdminVariationBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private val autoCompleteTextView = binding.dropdownMenu.editText as AutoCompleteTextView

        private val adapter =
            CustomDropdownAdapter(binding.root.context, R.layout.item_custom_dropdown)

        fun bindData(variation: VariationModel) {
            binding.variation = variation
            val data = variation.variationOptions.map {
                Pair(it.id, it.value)
            }
            adapter.setItems(data)

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
            autoCompleteTextView.setAdapter(adapter)
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VariationAdminViewHolder {
        val binding =
            ItemAdminVariationBinding.inflate(LayoutInflater.from(context), parent, false)
        return VariationAdminViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VariationAdminViewHolder, position: Int) {
        holder.bindData(items[position])
    }

    override fun setItems(items: List<VariationModel>) {
        this.items = items as ArrayList<VariationModel>
        notifyDataSetChanged()
    }
}