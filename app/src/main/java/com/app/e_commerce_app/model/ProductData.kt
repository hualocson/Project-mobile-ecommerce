package com.app.e_commerce_app.model

import com.google.gson.annotations.SerializedName

data class ProductData(
        @SerializedName("products")
        var products : ArrayList<ProductModel>
)