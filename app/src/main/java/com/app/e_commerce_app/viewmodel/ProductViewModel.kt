package com.app.e_commerce_app.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.data.repository.ProductRepository
import com.app.e_commerce_app.utils.Resource
import kotlinx.coroutines.Dispatchers

class ProductViewModel(application: Application) : ViewModel() {
    private val productRepository: ProductRepository = ProductRepository()

    fun getAllProducts() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))

        when (val response = productRepository.getAllProducts()) {
            is NetWorkResult.Success -> emit(Resource.success(response.data.data))
            is NetWorkResult.Error -> emit(Resource.error(null, response.message))
            is NetWorkResult.Exception -> emit(Resource.error(null, response.e.message))
        }
    }

    fun getProductsByCategory(id : Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))

        when (val response = productRepository.getProductsByCategory(id)) {
            is NetWorkResult.Success -> emit(Resource.success(response.data.data))
            is NetWorkResult.Error -> emit(Resource.error(null, response.message))
            is NetWorkResult.Exception -> emit(Resource.error(null, response.e.message))
        }
    }

    class ProductViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ProductViewModel(application) as T
            }
            throw IllegalArgumentException("Unable construct viewModel")
        }
    }
}