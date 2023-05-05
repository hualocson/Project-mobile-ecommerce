package com.app.e_commerce_app.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.app.e_commerce_app.base.BaseViewModel
import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.data.repository.ProductRepository
import com.app.e_commerce_app.data.services.ProductRemoteService
import com.app.e_commerce_app.model.product.ProductModel
import com.app.e_commerce_app.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : BaseViewModel() {
    private val productRemoteService = ProductRemoteService()
    private val productRepository: ProductRepository = ProductRepository(productRemoteService)

    private val _productsData = MutableLiveData<List<ProductModel>>()
    val productsData: LiveData<List<ProductModel>> = _productsData
    private val _productDetailData = MutableLiveData<ProductModel>()
    val productDetailData: LiveData<ProductModel> = _productDetailData

//    fun getAllProducts() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(null))
//
//        when (val response = productRepository.getAllProducts()) {
//            is NetWorkResult.Success -> response.data.data.let { products ->
//                _productsData.postValue(products!!.products)
//                emit(Resource.success(products))
//            }
//            is NetWorkResult.Error -> emit(Resource.error(null, response.exception.message))
//        }
//    }

    fun getProductsByCategory(id: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))

        when (val response = productRepository.getProductsByCategory(id)) {
            is NetWorkResult.Success -> emit(Resource.success(response.data.data))
            is NetWorkResult.Error -> emit(Resource.error(null, response.exception.message))
        }
    }

    fun getProductsById(id: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        when (val response = productRepository.getProductsById(id)) {
            is NetWorkResult.Success -> emit(Resource.success(response.data.data))
            is NetWorkResult.Error -> emit(Resource.error(null, response.exception.message))
        }
    }

    fun fetchAllProducts() {
        showLoading(true)
        parentJob = viewModelScope.launch(Dispatchers.IO) {
            val products = productRepository.getAllProducts()
            _productsData.postValue(products)
        }
        registerJobFinish()
    }

//    fun fetchProductByCategoryId(id: Int) {
//        showLoading(true)
//        parentJob = viewModelScope.launch(Dispatchers.IO) {
//            val response = productRepository.getProductsByCategory(id)
//            if (response is NetWorkResult.Success) {
//                _productsData.postValue(response.data.data!!.products)
//            }
//        }
//        registerJobFinish()
//    }

    fun fetchProductDetail(id: Int) {
        showLoading(true)
        Log.d("LOAD in PRODUCT:", "LOAD")
        parentJob = viewModelScope.launch(Dispatchers.IO) {
            val response = productRepository.getProductsById(id)
            if (response is NetWorkResult.Success) {
                _productDetailData.postValue(response.data.data!!)
            }
        }
        registerJobFinish()
    }

    class ProductViewModelFactory(private val application: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ProductViewModel(application) as T
            }
            throw IllegalArgumentException("Unable construct viewModel")
        }
    }
}