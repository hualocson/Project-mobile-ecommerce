package com.app.e_commerce_app.model

data class CategoryRequest(
    val categoryName: String, val icUrl: String, val variations: List<String>
)
