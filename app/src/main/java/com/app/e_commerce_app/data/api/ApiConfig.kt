package com.app.e_commerce_app.data.api

import com.app.e_commerce_app.MyApplication
import com.app.e_commerce_app.base.network.BaseNetworkException
import com.app.e_commerce_app.base.network.NetworkErrorException
import com.app.e_commerce_app.common.AppSharePreference
import com.app.e_commerce_app.common.AuthInterceptor
import com.app.e_commerce_app.data.repository.TokenRepository
import com.app.e_commerce_app.model.CustomResponse
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiConfig {
    private fun getRetrofitInstance(): Retrofit {
        val context = MyApplication.appContext // Get the application context
        val appSharePreference = AppSharePreference(context) // Create an instance of AppSharePreference

        return Retrofit.Builder()
            .client(getOkHttpClient(appSharePreference))
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
    }

    private fun getOkHttpClient(appSharePreference: AppSharePreference): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(TokenRepository(appSharePreference)))
            .build()
    }

    val userApi: UserApi by lazy {
        getRetrofitInstance().create(UserApi::class.java)
    }

    val categoryApi: CategoryApi by lazy {
        getRetrofitInstance().create(CategoryApi::class.java)
    }

    val productApi: ProductApi by lazy {
        getRetrofitInstance().create(ProductApi::class.java)
    }

    val variationApi: VariationApi by lazy {
        getRetrofitInstance().create(VariationApi::class.java)
    }

    suspend fun <T : Any> handleApi(execute: suspend () -> Response<T>): NetWorkResult<T> {
        val response: Response<T>
        try {
            response = execute()
        }
        catch (t: Throwable) {
            return NetWorkResult.Error(parseNetworkErrorException(t))
        }
        return if (response.isSuccessful) {
            if (response.body() == null) {
                val errBody: CustomResponse<Nothing> = Gson().fromJson<CustomResponse<Nothing>>(
                    response.errorBody()?.string(),
                    CustomResponse::class.java
                )
                NetWorkResult.Error(BaseNetworkException(responseMessage =  "Response without body, Message ${errBody.message}", responseCode = 200))
            } else {
                NetWorkResult.Success(response.body()!!)
            }
        } else {
            val errorBody = response.errorBody()?.string() ?: ""
            NetWorkResult.Error(parseError(response.message(), response.code(), errorBody))
        }
    }

    private  fun parseError(
        responseMessage: String?,
        responseCode: Int,
        errorBody: String?
    ): BaseNetworkException {

        val baseNetworkException =  BaseNetworkException(responseMessage,responseCode)
        errorBody?.let{
            baseNetworkException.parseFromString(it)
        }

        return baseNetworkException
    }

    private fun parseNetworkErrorException(throwable: Throwable): NetworkErrorException {
        return NetworkErrorException(throwable.message)
    }


}