package com.app.e_commerce_app.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.app.e_commerce_app.base.BaseViewModel
import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.data.repository.CategoryRepository
import com.app.e_commerce_app.model.CategoryModel
import com.app.e_commerce_app.model.LoginRequest
import com.app.e_commerce_app.model.UserJson
import com.app.e_commerce_app.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application) : BaseViewModel() {
    private val categoryRepository: CategoryRepository = CategoryRepository()

    private val _categoriesData = MutableLiveData<List<CategoryModel>>()
    val categoriesData: LiveData<List<CategoryModel>> = _categoriesData
    fun getAllCategories() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        showLoading(true)

        when (val response = categoryRepository.getAllCategories()) {
            is NetWorkResult.Success -> response.data.data.let {categoryData ->
                _categoriesData.postValue(categoryData!!.categories)
                emit(Resource.success(categoryData))
            }
            is NetWorkResult.Error -> emit(Resource.error(null, response.message))
            is NetWorkResult.Exception -> emit(Resource.error(null, response.e.message))
        }
        registerJobFinish()
    }

    fun fetchAllCategories() {
        showLoading(true)
        parentJob = viewModelScope.launch(Dispatchers.IO) {
            val response = categoryRepository.getAllCategories()
            if (response is NetWorkResult.Success) {
                _categoriesData.postValue(response.data.data!!.categories)
            }
        }
        registerJobFinish()
    }

    class CategoryViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CategoryViewModel(application) as T
            }
            throw IllegalArgumentException("Unable construct viewModel")
        }
    }
}