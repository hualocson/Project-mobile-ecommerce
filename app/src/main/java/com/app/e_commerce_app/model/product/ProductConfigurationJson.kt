package com.app.e_commerce_app.model.product

import android.os.Parcelable
import com.app.e_commerce_app.model.variation.VariationModel
import com.app.e_commerce_app.model.variation.VariationOptionModel
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductConfigurationJson(
    val id: Int,
    val productItemId: Int,
    val variation: String,
    val value: String,
    val variationId: Int
) : Parcelable {
    fun toVariationOptionModel() : VariationOptionModel {
        return VariationOptionModel(
            id = id,
            value = value,
            variationId = variationId
        )
    }
}
