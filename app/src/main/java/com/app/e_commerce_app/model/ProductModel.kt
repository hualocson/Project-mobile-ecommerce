package com.app.e_commerce_app.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class ProductModel(
    val id: Int,
    val categoryId: Int,
    val name: String,
    val description: String,
    val productImage: String,
    val productItems: ArrayList<ProductItemJson>? = null,
    val minPrice: Int,
) : Parcelable {
    override fun toString(): String {
        return "${id.toString()}  ${name.toString()}"
    }
}