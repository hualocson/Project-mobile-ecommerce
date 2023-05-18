package com.app.e_commerce_app.ui.admin.category.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.e_commerce_app.base.BaseViewModel
import com.app.e_commerce_app.data.repository.CategoryRepository
import com.app.e_commerce_app.data.repository.VariationRepository
import com.app.e_commerce_app.model.CategoryModel
import com.app.e_commerce_app.model.CategoryRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminEditCategoryViewModel @Inject constructor(
    private val variationRepository: VariationRepository,
    private val categoryRepository: CategoryRepository
) : BaseViewModel() {

    private val _items = MutableLiveData<List<String>>(listOf())
    val items: LiveData<List<String>> = _items

    private var _categoryItem = MutableLiveData<CategoryModel>()
    val categoryItem: LiveData<CategoryModel> = _categoryItem

    fun clearData() {
        _items.postValue(listOf())
        _categoryItem.postValue(CategoryModel(0, "", ""))
    }

    fun updateCategory(id: Int, categoryRequest: CategoryRequest) {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            categoryRepository.updateCategory(id, categoryRequest)
        }
        registerJobFinish()
    }

    fun fetchData(categoryItem: CategoryModel) {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val data = variationRepository.getVariationsInCategory(categoryItem.id)
            val list = data.map {
                it.name
            }
            _categoryItem.postValue(categoryItem)
            _items.postValue(list)
        }
        registerJobFinish()
    }

    fun addCategory(categoryRequest: CategoryRequest) {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            categoryRepository.addCategory(categoryRequest)
        }
        registerJobFinish()
    }


    fun addToList(variationAdded: String) {
        val currentList = _items.value.orEmpty().toMutableList()
        if (variationAdded.isNotBlank()) {
            val existingIndex =
                currentList.indexOfFirst { it == variationAdded }

            if (existingIndex == -1) {
                currentList.add(variationAdded)
            }
        }
        _items.postValue(currentList)
    }

    fun removeFromList(variation: String) {
        val currentList = _items.value.orEmpty().toMutableList()
        if (variation.isNotBlank()) {
            val existingIndex =
                currentList.indexOfFirst { it == variation }

            if (existingIndex != -1) {
                currentList.removeAt(existingIndex)
            }
        }
        _items.postValue(currentList)
    }

    fun updateData(old: String, newVariation: String) {
        val currentList = _items.value.orEmpty().toMutableList()
        if (newVariation.isNotBlank() && old !== newVariation) {
            val oldIndex =
                currentList.indexOfFirst { it == old }

            if (oldIndex != -1) {
                currentList[oldIndex] = newVariation
            }
        }
        _items.postValue(currentList)
    }
}