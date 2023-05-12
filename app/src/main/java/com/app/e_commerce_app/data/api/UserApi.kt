package com.app.e_commerce_app.data.api

import com.app.e_commerce_app.model.*
import com.app.e_commerce_app.model.token.TokenJson
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {
    @POST(ConstantsURL.LOGIN_URL)
    suspend fun login(@Body loginRequest: LoginRequest): Response<CustomResponse<TokenJson>>

    @POST(ConstantsURL.REGISTER_URL)
    suspend fun register(@Body registerRequest: RegisterRequest): Response<CustomResponse<UserJson>>

    @GET(ConstantsURL.USER_PROFILE)
    suspend fun getUserProfile(): Response<CustomResponse<UserJson>>

    @PATCH(ConstantsURL.EDIT_PROFILE)
    suspend fun updateUserProfile(userJson: UserJson): Response<CustomResponse<UserJson>>

    @POST(ConstantsURL.CHECK_EMAIL)
    suspend fun checkEmail(@Body email: PreSignupRequest): Response<CustomResponse<CheckEmailResponse>>

    @GET(ConstantsURL.USER_ADDRESS)
    suspend fun getUserAddresses(@Query("flag") flag: String = ""): Response<CustomResponse<List<AddressJson>>>

    @POST(ConstantsURL.USER_ADDRESS)
    suspend fun addAddress(@Body addressRequest: AddressRequest): Response<CustomResponse<AddressJson>>

    @PATCH(ConstantsURL.USER_ADDRESS_UPDATE)
    suspend fun updateAddress(@Path("addressId") addressId: Int, @Body addressRequest: AddressRequest) : Response<CustomResponse<AddressJson>>

    @Multipart
    @PATCH(ConstantsURL.USER_UPLOAD_IMG)
    suspend fun uploadImage(@Part avatar: MultipartBody.Part): Response<CustomResponse<UserJson>>
}