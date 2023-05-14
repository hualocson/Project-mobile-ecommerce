package com.app.e_commerce_app.model.product

import android.os.Parcelable
import android.util.Log
import com.app.e_commerce_app.model.variation.VariationModel
import com.app.e_commerce_app.model.variation.VariationOptionModel
import com.app.e_commerce_app.utils.Utils
import com.denzcoskun.imageslider.models.SlideModel
import kotlinx.parcelize.Parcelize
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
data class ProductModel(
    val id: Int,
    val categoryId: Int,
    val name: String,
    val description: String,
    val productImage: String,
    val productItems: ArrayList<ProductItemJson>? = null,
    val minPrice: Long,
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
        get() : String = Utils.formatNumber(minPrice)
    private val productItemOptions
        get() : List<ProductConfigurationJson> {
            val arr = ArrayList<ProductConfigurationJson>()
            if (productItems.isNullOrEmpty())
                return arr
            productItems.map { item ->
                item.productConfigurations.map { config ->
                    arr.add(
                        ProductConfigurationJson(
                            config.id,
                            item.id,
                            config.variation,
                            config.value
                        )
                    )
                }
            }
            Log.d("ProdetailViewModel", arr.toString())
            return arr
        }

    val variations
        get() : List<VariationModel> {
            val arr = ArrayList<VariationModel>()
            if (productItemOptions.isEmpty())
                return ArrayList<VariationModel>()
            productItemOptions.map { option ->
                if (arr.find { it.id == option.id } == null)
                    arr.add(VariationModel(option.id, categoryId, option.variation))
            }
            arr.map { variation ->
                productItemOptions.map {
                    if (it.id == variation.id)
                        variation.variationOptions.add(VariationOptionModel(it.productItemId, it.value, it.id))
                }
            }
            return arr
        }
}