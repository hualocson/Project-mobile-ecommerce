package com.app.e_commerce_app.ui.admin.products.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.e_commerce_app.R
import com.app.e_commerce_app.base.BaseViewModel
import com.app.e_commerce_app.base.network.BaseNetworkException
import com.app.e_commerce_app.common.Event
import com.app.e_commerce_app.data.repository.ProductRepository
import com.app.e_commerce_app.data.repository.VariationRepository
import com.app.e_commerce_app.model.product.ProductItemJson
import com.app.e_commerce_app.model.product.ProductItemRequest
import com.app.e_commerce_app.model.variation.VariationModel
import com.app.e_commerce_app.model.variation.VariationOptionModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
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


    private val _itemsData = MutableLiveData<List<Pair<Int, String>>>()
    val itemsData: LiveData<List<Pair<Int, String>>> = _itemsData

    private val _itemData = MutableLiveData<ProductItemJson>()
    val itemData: LiveData<ProductItemJson> = _itemData

    val isResponseSuccess = MutableLiveData(Event(false))

    init {
        _productConfigurationsData.observeForever { listOptions ->
            _variationsData.value?.let {
                if (it.isNotEmpty() && listOptions.size == it.size)
                    _isEnableCreate.postValue(true)
            }
        }
    }

    fun fetchData(productId: Int, productItemId: Int, categoryId: Int) {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val responseDef = async { variationRepository.getVariationsInCategory(0) }
            val productDef = async { productRepository.getProductsById(productId) }
            _variationsData.postValue(responseDef.await())
            if (productItemId != 0) {
                val item = productDef.await().productItems?.find {
                    it.id == productItemId
                }
                item?.let {
                    _itemData.postValue(it)
                    val list = it.productConfigurations.map { variation ->
                        variation.toVariationOptionModel()
                    }
                    val sorted = list.sortedBy { option ->
                        option.variationId
                    }
                    _productConfigurationsData.postValue(sorted)
                    _itemsData.postValue(sorted.map { optionValue ->
                        Pair(optionValue.id, optionValue.value)
                    })
                }
            }
        }
        registerJobFinish()
    }

    fun addProductItem(productId: Int, productItemRequest: ProductItemRequest) {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            productRepository.addProductItem(productId, productItemRequest)
            isResponseSuccess.postValue(Event(true))
        }
        registerJobFinish()
    }

    fun updateProductItem(itemId: Int, createProductItemRequest: ProductItemRequest) {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            productRepository.updateProductItem(itemId, createProductItemRequest)
            isResponseSuccess.postValue(Event(true))
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

    override fun parseErrorCallApi(e: Throwable) {
        super.parseErrorCallApi(e)
        isResponseSuccess.postValue(Event(false))
    }
}