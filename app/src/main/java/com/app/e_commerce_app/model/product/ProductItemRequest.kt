package com.app.e_commerce_app.model.product

import com.app.e_commerce_app.model.variation.VariationOptionModel

data class ProductItemRequest(
    val qtyInStock: Int,
    val price: Long,
    val imageUrl: String,
    val productConfigurations: List<VariationOptionModel>
) {
}