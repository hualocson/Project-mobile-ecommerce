package com.app.e_commerce_app.model.variation

import android.os.Parcelable
import com.app.e_commerce_app.utils.OnVariationOptionClick
import kotlinx.parcelize.Parcelize

@Parcelize
data class VariationGetAllModel(
    val id: Int,
    val categoryId: Int,
    val name: String,
) : Parcelable