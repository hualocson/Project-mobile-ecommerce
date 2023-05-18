package com.app.e_commerce_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
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
class StoreViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository
) : BaseViewModel() {

    private var _categoryRadioData = MutableLiveData<List<CategoryRadioButton>>()
    val categoryRadioData: LiveData<List<CategoryRadioButton>> = _categoryRadioData


    private var _productsData = MutableLiveData<List<ProductModel>>()
    val productsData: LiveData<List<ProductModel>> = _productsData


    fun getProductsByCategory(id: Int) {
        showLoading(true)
        if (id != 0) {
            parentJob = viewModelScope.launch(handler) {
                val products = productRepository.getProductsByCategory(id)
                _productsData.postValue(products)
            }
        } else {
            fetchData()
        }

        registerJobFinish()
    }

    private fun toListCategoryRadioButton(categories: List<CategoryModel>): List<CategoryRadioButton> {
        val arr = ArrayList<CategoryRadioButton>()
        categories.forEach {
            arr.add(it.toCategoryRadio())
        }
        return arr
    }


    private fun fetchData() {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val categoriesDeferred = async { categoryRepository.getAllCategories() }
            val productsDeferred = async { productRepository.getAllProducts() }

            _categoryRadioData.postValue(toListCategoryRadioButton(categoriesDeferred.await()))
            _productsData.postValue(productsDeferred.await())
        }
        registerJobFinish()
    }
}