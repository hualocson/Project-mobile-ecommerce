package com.app.e_commerce_app.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.data.repository.ProductRepository
import com.app.e_commerce_app.model.CategoryModel
import com.app.e_commerce_app.model.ProductData
import com.app.e_commerce_app.model.ProductModel
import com.app.e_commerce_app.utils.Resource
import kotlinx.coroutines.Dispatchers

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val productRepository: ProductRepository = ProductRepository()

    private val _productsData = MutableLiveData<List<ProductModel>>()
    val productsData: LiveData<List<ProductModel>> = _productsData

    fun getAllProducts() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))

        when (val response = productRepository.getAllProducts()) {
            is NetWorkResult.Success -> response.data.data.let { products ->
                _productsData.postValue(products!!.products)
                emit(Resource.success(products))
            }
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

    fun getProductsById(id : Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        when (val response = productRepository.getProductsById(id)) {
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