package com.app.e_commerce_app.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.data.repository.CategoryRepository
import com.app.e_commerce_app.model.CategoryModel
import com.app.e_commerce_app.model.LoginRequest
import com.app.e_commerce_app.model.UserJson
import com.app.e_commerce_app.utils.Resource
import kotlinx.coroutines.Dispatchers

class CategoryViewModel(application: Application) : AndroidViewModel(application) {
    private val categoryRepository: CategoryRepository = CategoryRepository()

    private val _categoriesData = MutableLiveData<List<CategoryModel>>()
    val categoriesData: LiveData<List<CategoryModel>> = _categoriesData
    fun getAllCategories() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))

        when (val response = categoryRepository.getAllCategories()) {
            is NetWorkResult.Success -> response.data.data.let {categories ->
                _categoriesData.postValue(categories!!.categories)
                emit(Resource.success(categories))
            }
            is NetWorkResult.Error -> emit(Resource.error(null, response.message))
            is NetWorkResult.Exception -> emit(Resource.error(null, response.e.message))
        }
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