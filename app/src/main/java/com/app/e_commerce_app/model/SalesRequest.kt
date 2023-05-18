package com.app.e_commerce_app.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SalesRequest(
    val title: String,
    val content: String,
    val author: String,
    val imageUrl: String
): Parcelable