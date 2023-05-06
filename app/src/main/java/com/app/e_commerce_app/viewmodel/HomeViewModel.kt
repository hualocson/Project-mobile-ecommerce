package com.app.e_commerce_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.e_commerce_app.base.BaseViewModel
import com.app.e_commerce_app.common.Event
import com.app.e_commerce_app.data.repository.CategoryRepository
import com.app.e_commerce_app.data.repository.ProductRepository
import com.app.e_commerce_app.data.repository.TokenRepository
import com.app.e_commerce_app.data.repository.UserRepository
import com.app.e_commerce_app.model.CategoryModel
import com.app.e_commerce_app.model.CategoryRadioButton
import com.app.e_commerce_app.model.UserJson
import com.app.e_commerce_app.model.product.ProductModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository,
    private val tokenRepository: TokenRepository
) : BaseViewModel() {

    private val _categoriesData = MutableLiveData<List<CategoryModel>>()
    val categoriesData: LiveData<List<CategoryModel>> = _categoriesData


    private val _categoryRadioData = MutableLiveData<List<CategoryRadioButton>>()
    val categoryRadioData: LiveData<List<CategoryRadioButton>> = _categoryRadioData


    private val _productsData = MutableLiveData<List<ProductModel>>()
    val productsData: LiveData<List<ProductModel>> = _productsData

    private val _userLiveData = MutableLiveData<UserJson>()
    val userLiveData: LiveData<UserJson> = _userLiveData

    var isProductsLoading = MutableLiveData<Event<Boolean>>()

    fun fetchAllProducts() {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val products = productRepository.getAllProducts()
            _productsData.postValue(products)
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

    fun fetchAllCategories() {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val response = categoryRepository.getAllCategories()
            _categoriesData.postValue(response!!.categories)
            _categoryRadioData.postValue(toListCategoryRadioButton(response.categories))
        }
        registerJobFinish()
    }

    fun fetchData() {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val categoriesDeferred = async { categoryRepository.getAllCategories() }
            val productsDeferred = async { productRepository.getAllProducts() }

            _categoriesData.postValue(categoriesDeferred.await()!!.categories)
            _categoryRadioData.postValue(toListCategoryRadioButton(categoriesDeferred.await()!!.categories))

            _productsData.postValue(productsDeferred.await())
        }
        registerJobFinish()
    }

    fun checkIsLogin(): Boolean {
        return tokenRepository.checkIsLogin()
    }

    fun fetchUser() {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val user = userRepository.getUserProfile()
            _userLiveData.postValue(user)
        }
        registerJobFinish()
    }

    fun fetchProductsByCategoryId(id: Int) {
        showLoading(false)
        parentJob = viewModelScope.launch(handler) {

            isProductsLoading.postValue(Event(true))
            val products = async { productRepository.getProductsByCategory(id) }
            _productsData.postValue(products.await())

            isProductsLoading.postValue(Event(false))
        }

        registerJobFinish()
    }
}