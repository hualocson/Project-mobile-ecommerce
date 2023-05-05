package com.app.e_commerce_app.data.repository

import com.app.e_commerce_app.data.api.ApiConfig
import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.data.services.UserRemoteService
import com.app.e_commerce_app.model.CustomResponse
import com.app.e_commerce_app.model.LoginRequest
import com.app.e_commerce_app.model.PreSignupRequest
import com.app.e_commerce_app.model.RegisterRequest
import com.app.e_commerce_app.model.UserJson
import com.app.e_commerce_app.model.*
import com.app.e_commerce_app.model.token.TokenJson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val userRemoteService: UserRemoteService) {
    suspend fun login(loginRequest: LoginRequest) = withContext(Dispatchers.IO) {
        when(val result = userRemoteService.login(loginRequest)){
            is NetWorkResult.Success -> {
                result.data.data!!.toTokenModel()
            }
            is NetWorkResult.Error -> {
                throw result.exception
            }
        }
    }

    suspend fun getUserProfile() = withContext(Dispatchers.IO) {
        when(val result = userRemoteService.getUserProfile()) {
            is NetWorkResult.Success -> {
                result.data.data!!
            }

            is NetWorkResult.Error -> {
                throw result.exception
            }
        }
    }

    suspend fun register(registerRequest: RegisterRequest) = withContext(Dispatchers.IO) {
        when(val result = userRemoteService.register(registerRequest)) {
            is NetWorkResult.Success -> {
                result.data.data
            }

            is NetWorkResult.Error -> {
                throw result.exception
            }
        }
    }

    suspend fun checkEmail(email: PreSignupRequest) = withContext(Dispatchers.IO) {
        when(val result = userRemoteService.checkEmail(email)) {
            is NetWorkResult.Success -> {
                result.data.data
            }

            is NetWorkResult.Error -> {
                throw result.exception
            }
        }
    }

    suspend fun getAllUserAddresses() = withContext(Dispatchers.IO) {
        when(val result = userRemoteService.getAllUserAddresses()) {
            is NetWorkResult.Success -> {
                result.data.data
            }

            is NetWorkResult.Error -> {
                throw result.exception
            }
        }
    }
}