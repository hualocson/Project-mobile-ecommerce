package com.app.e_commerce_app.model.product

import android.os.Parcelable
import com.app.e_commerce_app.model.variation.VariationModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductDetailModel(
    val product: ProductModel,
    val variations: List<VariationModel>
) : Parcelable
