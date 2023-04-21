package com.app.e_commerce_app.model

import com.google.gson.annotations.SerializedName

data class CategoryModel(
    @SerializedName("id")
    val id : Int,

    @SerializedName("categoryName")
    val categoryName: String,

    @SerializedName("icUrl")
    val categoryIc: String,
)
