package com.app.e_commerce_app.ui.admin.products.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.e_commerce_app.base.BaseViewModel
import com.app.e_commerce_app.data.repository.CategoryRepository
import com.app.e_commerce_app.data.repository.ProductRepository
import com.app.e_commerce_app.model.ChooseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminProductItemViewModel @Inject constructor(
    private val productRepository: ProductRepository,
) : BaseViewModel() {

    private val _items = MutableLiveData<List<ChooseItem>>()
    val items: LiveData<List<ChooseItem>> = _items

    private val _categoryId = MutableLiveData<Int>()
    val categoryIdData: LiveData<Int> = _categoryId

    fun getListProductItemInProduct(id: Int) {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val product = productRepository.getProductsById(id)
            val data = product.productItems!!.map {
                it.toAdminItem(product.name)
            }
            _categoryId.postValue(product.categoryId)
            _items.postValue(data)
        }
        registerJobFinish()
    }
}