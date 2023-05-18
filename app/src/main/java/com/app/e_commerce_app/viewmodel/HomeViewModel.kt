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
import com.app.e_commerce_app.model.UserJson
import com.app.e_commerce_app.model.CategoryModel
import com.app.e_commerce_app.model.CategoryRadioButton
import com.app.e_commerce_app.model.product.ProductModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository,
    private val tokenRepository: TokenRepository
) : BaseViewModel() {

    private var _categoriesData = MutableLiveData<List<CategoryModel>>()
    val categoriesData: LiveData<List<CategoryModel>> = _categoriesData


    private var _categoryRadioData = MutableLiveData<List<CategoryRadioButton>>()
    val categoryRadioData: LiveData<List<CategoryRadioButton>> = _categoryRadioData


    private var _productsData = MutableLiveData<List<ProductModel>>()
    val productsData: LiveData<List<ProductModel>> = _productsData

    private var _userLiveData = MutableLiveData<UserJson>()
    val userLiveData: LiveData<UserJson> = _userLiveData

    var isProductsLoading = MutableLiveData<Event<Boolean>>()

    var isFetchDataSuccess = MutableLiveData<Event<Boolean>>()

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
            val categories = categoryRepository.getAllCategories()
            _categoriesData.postValue(categories)
            _categoryRadioData.postValue(toListCategoryRadioButton(categories))
        }
        registerJobFinish()
    }

    fun fetchData() {
        showLoading(true)
        isFetchDataSuccess.postValue(Event(false))
        parentJob = viewModelScope.launch(handler) {
            delay(1000)

            val categoriesDeferred = async { categoryRepository.getAllCategories() }
            val productsDeferred = async { productRepository.getAllProducts() }
            val userDeferred = async { userRepository.getUserProfile() }

            _userLiveData.postValue(userDeferred.await())

            val fetchedCategories = categoriesDeferred.await()

            _categoriesData.postValue(fetchedCategories)
            _categoryRadioData.postValue(toListCategoryRadioButton(fetchedCategories))
            _productsData.postValue(productsDeferred.await())
            isFetchDataSuccess.postValue(Event(true))
        }
        registerJobFinish()
    }

    fun checkIsLogin(): Boolean {
        return tokenRepository.checkIsLogin()
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