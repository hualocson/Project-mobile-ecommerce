package com.app.e_commerce_app.ui.admin.products.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.e_commerce_app.base.BaseViewModel
import com.app.e_commerce_app.common.Event
import com.app.e_commerce_app.data.repository.CategoryRepository
import com.app.e_commerce_app.data.repository.ProductRepository
import com.app.e_commerce_app.model.CategoryModel
import com.app.e_commerce_app.model.product.ProductModel
import com.app.e_commerce_app.model.product.ProductRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminEditProductViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository
) :
    BaseViewModel() {
    private val _categoriesData = MutableLiveData<List<CategoryModel>>()
    val categoriesData: LiveData<List<CategoryModel>> = _categoriesData

    private val _productData = MutableLiveData<ProductModel>()
    val productData: LiveData<ProductModel> = _productData

    val isResponseSuccess = MutableLiveData(Event(false))

    fun fetchAllCategories() {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val response = categoryRepository.getAllCategories()
            response?.let {
                _categoriesData.postValue(it.categories)
            }
        }
        registerJobFinish()
    }


    fun getProductById(id: Int) {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val product = productRepository.getProductsById(id)
            _productData.postValue(product)
            val category = categoryRepository.getCategoryById(product.categoryId)
            category?.let {
                _categoriesData.postValue(listOf(it))
            }
        }
        registerJobFinish()
    }

    fun updateProduct(id: Int, productRequest: ProductRequest) {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            productRepository.updateProduct(id, productRequest)
            isResponseSuccess.postValue(Event(true))
        }
        registerJobFinish()
    }

    fun addProduct(productRequest: ProductRequest) {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            productRepository.addProduct(productRequest)
            isResponseSuccess.postValue(Event(true))
        }
        registerJobFinish()
    }

    override fun parseErrorCallApi(e: Throwable) {
        super.parseErrorCallApi(e)
        isResponseSuccess.postValue(Event(false))
    }
}