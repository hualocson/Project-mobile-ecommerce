package com.app.e_commerce_app.ui.admin.adapter.adapterInterface

import com.app.e_commerce_app.model.variation.VariationOptionModel

interface DropDownItemSelectedListener {
    fun onItemSelected(selectedItem: VariationOptionModel)
}