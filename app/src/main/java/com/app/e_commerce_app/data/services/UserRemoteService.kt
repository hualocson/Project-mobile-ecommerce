package com.app.e_commerce_app.data.services

import com.app.e_commerce_app.base.network.BaseRemoteService
import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.data.api.UserApi
import com.app.e_commerce_app.model.*
import com.app.e_commerce_app.model.token.TokenJson
import okhttp3.MultipartBody
import javax.inject.Inject

class UserRemoteService @Inject constructor(private val userApi: UserApi) : BaseRemoteService() {
    suspend fun login(loginRequest: LoginRequest): NetWorkResult<CustomResponse<TokenJson>> =
        handleApi { userApi.login(loginRequest) }

    suspend fun getUserProfile(): NetWorkResult<CustomResponse<UserJson>> =
        handleApi { userApi.getUserProfile() }

    suspend fun updateUserProfile(userJson: UserJson): NetWorkResult<CustomResponse<UserJson>> =
        handleApi { userApi.updateUserProfile(userJson) }

    suspend fun register(registerRequest: RegisterRequest): NetWorkResult<CustomResponse<UserJson>> =
        handleApi { userApi.register(registerRequest) }

    suspend fun checkEmail(email: PreSignupRequest): NetWorkResult<CustomResponse<ResponseWithMessage>> =
        handleApi { userApi.checkEmail(email) }

    suspend fun checkPassword(password: ChangePasswordRequest): NetWorkResult<CustomResponse<ResponseWithMessage>> =
        handleApi { userApi.checkPassword(password) }

    suspend fun getAllUserAddresses(): NetWorkResult<CustomResponse<List<AddressJson>>> =
        handleApi { userApi.getUserAddresses() }

    suspend fun getUserAddresses(flag: String): NetWorkResult<CustomResponse<List<AddressJson>>> =
        handleApi { userApi.getUserAddresses(flag) }


    suspend fun addAddress(addressRequest: AddressRequest): NetWorkResult<CustomResponse<AddressJson>> =
        handleApi { userApi.addAddress(addressRequest) }

    suspend fun uploadImage(avatar: MultipartBody.Part): NetWorkResult<CustomResponse<UserJson>> =
        handleApi { userApi.uploadImage(avatar) }

    suspend fun updateAddress(
        addressId: Int,
        addressRequest: AddressRequest
    ): NetWorkResult<CustomResponse<AddressJson>> =
        handleApi { userApi.updateAddress(addressId, addressRequest) }
    suspend fun getAllUsers(): NetWorkResult<CustomResponse<List<UserJson>>> =
        handleApi { userApi.getAllUsers() }
}