package com.app.e_commerce_app.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SaleJson(
    val id: Int,
    val title: String,
    val content: String,
    val imageUrl: String,
    val author: String,
    val date: String,
) : Parcelable {
}
