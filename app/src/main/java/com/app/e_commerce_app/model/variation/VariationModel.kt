package com.app.e_commerce_app.model.variation

import android.os.Parcelable
import com.app.e_commerce_app.utils.OnVariationOptionClick
import kotlinx.parcelize.Parcelize

@Parcelize
data class VariationModel(
    val id: Int,
    val categoryId: Int,
    val name: String,
    val variationOptions: ArrayList<VariationOptionModel> = ArrayList()
) : Parcelable