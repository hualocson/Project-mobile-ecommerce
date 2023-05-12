package com.app.e_commerce_app.model

import android.os.Parcelable
import com.app.e_commerce_app.R
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class NewJson(
    val id: Int,
    val title: String,
    val content: String,
    val imageUrl: String,
    val author: String,
    val date: String,
) : Parcelable {
}
