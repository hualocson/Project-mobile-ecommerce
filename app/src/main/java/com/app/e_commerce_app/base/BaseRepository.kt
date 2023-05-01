package com.app.e_commerce_app.base

import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.model.CustomResponse

interface BaseRepository<T> {
    suspend fun create(item: T): NetWorkResult<CustomResponse<T>>
    suspend fun read(id: String):  NetWorkResult<CustomResponse<T>>
    suspend fun update(item: T):  NetWorkResult<CustomResponse<T>>
    suspend fun delete(id: String):  NetWorkResult<CustomResponse<T>>
}