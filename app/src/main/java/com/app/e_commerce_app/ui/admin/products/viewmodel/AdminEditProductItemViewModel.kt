package com.app.e_commerce_app.ui.admin.products.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.e_commerce_app.base.BaseViewModel
import com.app.e_commerce_app.data.repository.ProductRepository
import com.app.e_commerce_app.data.repository.VariationRepository
import com.app.e_commerce_app.model.product.ProductItemRequest
import com.app.e_commerce_app.model.variation.VariationModel
import com.app.e_commerce_app.model.variation.VariationOptionModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminEditProductItemViewModel @Inject constructor(
    private val variationRepository: VariationRepository,
    private val productRepository: ProductRepository
) :
    BaseViewModel() {
    private val _variationsData = MutableLiveData<List<VariationModel>>()
    val variationsData: LiveData<List<VariationModel>> = _variationsData

    private val _isEnableCreate = MutableLiveData<Boolean>()
    val isEnableCreate: LiveData<Boolean> = _isEnableCreate

    private val _productConfigurationsData = MutableLiveData<List<VariationOptionModel>>()
    val productConfigurationsData: LiveData<List<VariationOptionModel>> = _productConfigurationsData

    fun fetchAllVariationInCategory(id: Int) {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val response = variationRepository.getVariationsInCategory(id)
            response?.let {
                _variationsData.postValue(it)
            }
        }
        registerJobFinish()
    }

    fun addProductItem(productId: Int, productItemRequest: ProductItemRequest) {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            productRepository.addProductItem(productId, productItemRequest)
            fetchAllVariationInCategory(_variationsData.value!!.first().categoryId)
        }
        registerJobFinish()
    }

    fun updateProductConfigurationsData(variationOption: VariationOptionModel) {
        val currentList = _productConfigurationsData.value.orEmpty().toMutableList()

        val existingIndex =
            currentList.indexOfFirst { it.variationId == variationOption.variationId }

        if (existingIndex != -1) {
            // VariationOptionModel already exists, update it
            currentList[existingIndex] = variationOption
        } else {
            // VariationOptionModel does not exist, add it to the list
            currentList.add(variationOption)
        }
        _productConfigurationsData.postValue(currentList)
        _variationsData.value?.let {
            if (it.isNotEmpty() && currentList.size == it.size) {
                _isEnableCreate.postValue(true)
            } else {
                _isEnableCreate.postValue(false)
            }
        }
    }
}