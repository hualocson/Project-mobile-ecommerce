package com.app.e_commerce_app.model.product

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductData(
    var products: List<ProductModel>
) : Parcelable