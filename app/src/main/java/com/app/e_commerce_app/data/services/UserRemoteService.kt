package com.app.e_commerce_app.data.services

import com.app.e_commerce_app.base.network.BaseRemoteService
import com.app.e_commerce_app.data.api.ApiConfig
import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.model.*
import com.app.e_commerce_app.model.token.TokenJson

class UserRemoteService : BaseRemoteService() {
    suspend fun login(loginRequest: LoginRequest): NetWorkResult<CustomResponse<TokenJson>> =
        handleApi { ApiConfig.userApi.login(loginRequest) }

    suspend fun getUserProfile(): NetWorkResult<CustomResponse<UserJson>> =
        ApiConfig.handleApi { ApiConfig.userApi.getUserProfile() }

    suspend fun register(registerRequest: RegisterRequest): NetWorkResult<CustomResponse<UserJson>> =
        ApiConfig.handleApi { ApiConfig.userApi.register(registerRequest) }

    suspend fun checkEmail(email: PreSignupRequest) =
        ApiConfig.handleApi { ApiConfig.userApi.checkEmail(email) }

    suspend fun getAllUserAddresses(): NetWorkResult<CustomResponse<List<AddressJson>>> =
        ApiConfig.handleApi { ApiConfig.userApi.getAllUserAddresses() }

}