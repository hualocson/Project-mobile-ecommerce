package com.app.e_commerce_app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.data.repository.CategoryRepository
import com.app.e_commerce_app.model.LoginRequest
import com.app.e_commerce_app.utils.Resource
import kotlinx.coroutines.Dispatchers

class CategoryViewModel(application: Application) : AndroidViewModel(application) {
    private val categoryRepository: CategoryRepository = CategoryRepository()

    fun getAllCategories() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))

        when (val response = categoryRepository.getAllCategories()) {
            is NetWorkResult.Success -> emit(Resource.success(response.data.data))
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