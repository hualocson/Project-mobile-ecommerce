package com.app.e_commerce_app.model.variation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VariationOptionModel(
    val id: Int,

    val value: String,
    val variationId: Int,

    var isChecked: Boolean = false
) : Parcelable {
}