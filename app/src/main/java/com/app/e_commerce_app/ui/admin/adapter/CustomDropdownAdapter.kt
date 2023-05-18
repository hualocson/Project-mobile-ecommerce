package com.app.e_commerce_app.ui.admin.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.app.e_commerce_app.BR

class CustomDropdownAdapter(
    context: Context,
    private val layoutResId: Int,
) : ArrayAdapter<Pair<Int, String>>(context, layoutResId) {

    private var items: List<Pair<Int, String>> = listOf()
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    override fun getItem(position: Int): Pair<Int, String> {
        return items[position]
    }

    private fun getCustomView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding

        if (convertView == null) {
            binding = DataBindingUtil.inflate(inflater, layoutResId, parent, false)
            binding.root.tag = binding
        } else {
            binding = convertView.tag as ViewDataBinding
        }

        val stringValue = items[position].second

        binding.setVariable(BR.item, stringValue)
        binding.executePendingBindings()

        return binding.root
    }

    fun  setItems(newItems: List<Pair<Int, String>>) {
        clear() // Clear existing items in the adapter
        this.items = newItems
        addAll(newItems) // Add new items to the adapter
    }
}
