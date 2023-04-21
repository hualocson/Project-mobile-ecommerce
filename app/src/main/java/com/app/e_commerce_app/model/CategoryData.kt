package com.app.e_commerce_app.model

import com.google.gson.annotations.SerializedName

data class CategoryData(
    @SerializedName("categories")
    var categories : ArrayList<CategoryModel>
)
