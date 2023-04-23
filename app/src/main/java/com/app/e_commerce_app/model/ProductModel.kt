package com.app.e_commerce_app.model

import com.google.gson.annotations.SerializedName

class ProductModel (
    @SerializedName("id")
    val id : Int,

    @SerializedName("categoryId")
    val categoryId : Int,

    @SerializedName("name")
    val name : String,

    @SerializedName("description")
    val description : String,

    @SerializedName("productImage")
    val productImage : String,

    @SerializedName("minPrice")
    val minPrice : Int,
)