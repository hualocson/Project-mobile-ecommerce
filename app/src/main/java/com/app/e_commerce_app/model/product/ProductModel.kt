package com.app.e_commerce_app.model.product

import android.os.Parcelable
import com.app.e_commerce_app.model.variation.VariationModel
import com.denzcoskun.imageslider.models.SlideModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductModel(
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

    val productImageList
        get() : ArrayList<SlideModel> {
            val imgList = ArrayList<SlideModel>()
            imgList.add(SlideModel(productImage))
            productItems!!.forEach { item ->
                imgList.add(SlideModel(item.productImage))
            }

            return imgList
        }

    val totalPrice
        get() : String {
            return minPrice.toString()
        }
}