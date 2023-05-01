package com.app.e_commerce_app.common

import com.app.e_commerce_app.data.repository.TokenRepository
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenRepository: TokenRepository): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        // Read the JWT token from SharedPreferences
        val authToken = tokenRepository.getAccessToken()

        // Add the token to the Authorization header
        if (!authToken.isNullOrEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer $authToken")
        }

        // Proceed with the request
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}