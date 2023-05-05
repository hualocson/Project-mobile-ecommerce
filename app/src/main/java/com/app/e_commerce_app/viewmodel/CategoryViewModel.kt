package com.app.e_commerce_app.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.app.e_commerce_app.base.BaseViewModel
import com.app.e_commerce_app.data.repository.CategoryRepository
import com.app.e_commerce_app.data.services.CategoryRemoteService
import com.app.e_commerce_app.model.CategoryModel
import com.app.e_commerce_app.model.CategoryRadioButton
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application) : BaseViewModel() {
    private val categoryRemoteService = CategoryRemoteService()
    private val categoryRepository: CategoryRepository = CategoryRepository(categoryRemoteService)

    private val _categoriesData = MutableLiveData<List<CategoryModel>>()
    val categoriesData: LiveData<List<CategoryModel>> = _categoriesData

    private val _categoryRadioData = MutableLiveData<List<CategoryRadioButton>>()
    val categoryRadioData: LiveData<List<CategoryRadioButton>> = _categoryRadioData

    private fun toListCategoryRadioButton(categoris: List<CategoryModel>) : List<CategoryRadioButton> {
        val arr = ArrayList<CategoryRadioButton>()
        categoris.forEach {
            arr.add(it.toCategoryRadio())
        }
        return arr
    }
//    fun getAllCategories() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(null))
//        showLoading(true)
//
//        when (val response = categoryRepository.getAllCategories()) {
//            is NetWorkResult.Success -> response.data.data.let {categoryData ->
//                _categoriesData.postValue(categoryData!!.categories)
//                emit(Resource.success(categoryData))
//            }
//            is NetWorkResult.Error -> emit(Resource.error(null, response.exception.message))
//        }
//        registerJobFinish()
//    }

    fun fetchAllCategories() {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val response = categoryRepository.getAllCategories()
            _categoriesData.postValue(response!!.categories)
            _categoryRadioData.postValue(toListCategoryRadioButton(response.categories))
        }
        registerJobFinish()
    }

    class CategoryViewModelFactory(private val application: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CategoryViewModel(application) as T
            }
            throw IllegalArgumentException("Unable construct viewModel")
        }
    }
}