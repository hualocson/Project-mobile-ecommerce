package com.app.e_commerce_app.ui.admin.category.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.e_commerce_app.base.BaseViewModel
import com.app.e_commerce_app.data.repository.CategoryRepository
import com.app.e_commerce_app.model.ChooseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminCategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : BaseViewModel() {

    private var _items = MutableLiveData<List<ChooseItem>>()
    val items: LiveData<List<ChooseItem>> = _items

    fun fetchAllProducts() {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val categories = categoryRepository.getAllCategories().map {
                it.toAdminChooseItem()
            }
            _items.postValue(categories)
        }
        registerJobFinish()
    }
}