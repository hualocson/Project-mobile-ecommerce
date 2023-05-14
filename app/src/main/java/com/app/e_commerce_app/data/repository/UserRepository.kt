package com.app.e_commerce_app.data.repository

import android.util.Log
import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.data.services.UserRemoteService
import com.app.e_commerce_app.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import javax.inject.Inject

class UserRepository @Inject constructor(private val userRemoteService: UserRemoteService) {
    suspend fun login(loginRequest: LoginRequest) = withContext(Dispatchers.IO) {
        when (val result = userRemoteService.login(loginRequest)) {
            is NetWorkResult.Success -> {
                result.data.data!!.toTokenModel()
            }
            is NetWorkResult.Error -> {
                throw result.exception
            }
        }
    }

    suspend fun getUserProfile() = withContext(Dispatchers.IO) {
        when (val result = userRemoteService.getUserProfile()) {
            is NetWorkResult.Success -> {
                result.data.data!!
            }

            is NetWorkResult.Error -> {
                throw result.exception
            }
        }
    }

    suspend fun updateUserProfile(userJson: UserJson) = withContext(Dispatchers.IO) {
        when (val result = userRemoteService.updateUserProfile(userJson)) {
            is NetWorkResult.Success -> {
                result.data.data!!
            }

            is NetWorkResult.Error -> {
                throw result.exception
            }
        }
    }

    suspend fun register(registerRequest: RegisterRequest) = withContext(Dispatchers.IO) {
        when (val result = userRemoteService.register(registerRequest)) {
            is NetWorkResult.Success -> {
                result.data.data
            }

            is NetWorkResult.Error -> {
                throw result.exception
            }
        }
    }

    suspend fun checkEmail(email: PreSignupRequest) = withContext(Dispatchers.IO) {
        when (val result = userRemoteService.checkEmail(email)) {
            is NetWorkResult.Success -> {
                result.data
            }

            is NetWorkResult.Error -> {
                throw result.exception
            }
        }
    }

    suspend fun checkPassword(password: ChangePasswordRequest) = withContext(Dispatchers.IO) {
        when (val result = userRemoteService.checkPassword(password)) {
            is NetWorkResult.Success -> {
                result.data
            }

            is NetWorkResult.Error -> {
                throw result.exception
            }
        }
    }

    suspend fun getAllUserAddresses() = withContext(Dispatchers.IO) {
        when (val result = userRemoteService.getAllUserAddresses()) {
            is NetWorkResult.Success -> {
                result.data.data
            }

            is NetWorkResult.Error -> {
                throw result.exception
            }
        }
    }

    suspend fun getDefaultAddress() = withContext(Dispatchers.IO) {
        when (val result = userRemoteService.getUserAddresses("default")) {
            is NetWorkResult.Success -> {
                result.data.data!!.first()
            }

            is NetWorkResult.Error -> {
                throw result.exception
            }
        }
    }

    suspend fun uploadImage(avatar: MultipartBody.Part) = withContext((Dispatchers.IO)) {
        when (val result = userRemoteService.uploadImage(avatar)){
            is NetWorkResult.Success -> {
                result.data.data
            }
            is NetWorkResult.Error -> {
                throw result.exception
            }
        }
    }

    suspend fun addAddress(addressRequest: AddressRequest) = withContext(Dispatchers.IO) {
        when (val response = userRemoteService.addAddress(addressRequest)) {
            is NetWorkResult.Success -> {
                response.data.data
            }
            is NetWorkResult.Error -> {
                throw response.exception
            }
        }
    }

    suspend fun updateAddress(addressId: Int, addressRequest: AddressRequest) =
        withContext(Dispatchers.IO) {
            when (val response = userRemoteService.updateAddress(addressId, addressRequest)) {
                is NetWorkResult.Success -> {
                    response.data.data
                }
                is NetWorkResult.Error -> {
                    throw response.exception
                }
            }
        }
}