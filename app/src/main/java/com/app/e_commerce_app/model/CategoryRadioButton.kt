package com.app.e_commerce_app.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class CategoryRadioButton(
    val id: Int,

    val categoryName: String,

    var isChecked: Boolean = false
) : Parcelable