package com.app.e_commerce_app.model

import android.os.Parcelable
import com.app.e_commerce_app.model.variation.VariationModel
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryModel(
    val id: Int,

    val categoryName: String,

    @SerializedName("icUrl")
    val categoryIc: String,
) : Parcelable {
    fun toCategoryRadio(): CategoryRadioButton {
        return CategoryRadioButton(id = id, categoryName = categoryName, isChecked = false)
    }
}
