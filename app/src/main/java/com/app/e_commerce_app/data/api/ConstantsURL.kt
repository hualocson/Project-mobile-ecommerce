package com.app.e_commerce_app.data.api

object ConstantsURL {
    // Endpoints
    const val BASE_URL = "http://10.0.2.2:5001/api/v1/" // For local api
//    const val BASE_URL = "https://server-rests-api-mobile.onrender.com/api/v1/"
//    const val BASE_URL = "https://server-rests-api-mobile-dev.onrender.com/api/v1/" //dev
    const val LOGIN_URL = "users/signin"
    const val USER_ADDRESS = "users/addresses"
    const val USER_ADDRESS_UPDATE = "users/addresses/{addressId}"
    const val USER_PROFILE = "users/profile"
    const val REGISTER_URL = "users/signup"
    const val CHECK_EMAIL = "users/email"
    const val CATEGORY_URL = "categories"
    const val VARIATION_IN_CATEGORY_URL = "categories/{id}/variations"
    const val PRODUCT_URL = "categories/products"
    const val PRODUCT_BY_CATEGORY_URL = "categories/{id}/products"
    const val PRODUCT_BY_ID = "categories/products/{productId}"
    const val USER_UPLOAD_IMG = "users/avatar"
    const val EDIT_PROFILE = "users"
}