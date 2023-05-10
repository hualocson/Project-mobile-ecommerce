package com.app.e_commerce_app.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.app.e_commerce_app.base.BaseViewModel
import com.app.e_commerce_app.data.repository.CategoryRepository
import com.app.e_commerce_app.data.repository.ProductRepository
import com.app.e_commerce_app.model.CategoryModel
import com.app.e_commerce_app.model.CategoryRadioButton
import com.app.e_commerce_app.model.product.ProductModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : BaseViewModel() {

    private val _productsData = MutableLiveData<List<ProductModel>>()
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