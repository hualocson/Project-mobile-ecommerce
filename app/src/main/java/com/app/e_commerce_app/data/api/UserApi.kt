package com.app.e_commerce_app.data.api

import com.app.e_commerce_app.model.*
import com.app.e_commerce_app.model.token.TokenJson
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {
    @POST(ConstantsURL.LOGIN_URL)
    suspend fun login(@Body loginRequest: LoginRequest): Response<CustomResponse<TokenJson>>

    @POST(ConstantsURL.REGISTER_URL)
    suspend fun register(@Body registerRequest: RegisterRequest): Response<CustomResponse<UserJson>>

    @GET(ConstantsURL.USER_PROFILE)
    suspend fun getUserProfile(): Response<CustomResponse<UserJson>>

    @POST(ConstantsURL.CHECK_EMAIL)
    suspend fun checkEmail(@Body email : PreSignupRequest) : Response<CustomResponse<CheckEmailResponse>>
    @GET(ConstantsURL.USER_ADDRESS)
    suspend fun getAllUserAddresses(): Response<CustomResponse<List<AddressJson>>>
}