package com.app.e_commerce_app.data.api

import com.app.e_commerce_app.model.CustomResponse
import com.google.gson.Gson
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    private fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
    }


    val userApi: UserApi by lazy {
        getRetrofitInstance().create(UserApi::class.java)
    }

    val categoryApi: CategoryApi by lazy {
        getRetrofitInstance().create(CategoryApi::class.java)
    }

    suspend fun <T : Any> handleApi(execute: suspend () -> Response<T>): NetWorkResult<T> {
        return try {
            val response = execute()
            val body = response.body()

            if (response.isSuccessful && body != null) {
                NetWorkResult.Success(body)
            } else {
                val errBody: CustomResponse<Nothing> = Gson().fromJson<CustomResponse<Nothing>>(
                    response.errorBody()?.string(),
                    CustomResponse::class.java
                )
                NetWorkResult.Error(code = response.code(), message = errBody.message)
            }
        } catch (e: HttpException) {
            NetWorkResult.Error(code = e.code(), message = e.message())
        } catch (e: Throwable) {
            NetWorkResult.Exception(e)
        }
    }
}