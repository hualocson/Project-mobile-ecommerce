package com.app.e_commerce_app.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryData(
    var categories: ArrayList<CategoryModel>
) : Parcelable
