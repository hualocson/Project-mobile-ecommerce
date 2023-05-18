package com.app.e_commerce_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.e_commerce_app.base.BaseViewModel
import com.app.e_commerce_app.data.repository.ProductRepository
import com.app.e_commerce_app.model.product.ProductModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : BaseViewModel() {

    private var _productsData = MutableLiveData<List<ProductModel>>()
    val productsData: LiveData<List<ProductModel>> = _productsData


    fun fetchAllProducts() {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val products = productRepository.getAllProducts()
            _productsData.postValue(products)
        }
        registerJobFinish()
    }
}