package com.app.e_commerce_app.ui.admin.varition.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.e_commerce_app.base.BaseViewModel
import com.app.e_commerce_app.data.repository.CategoryRepository
import com.app.e_commerce_app.data.repository.UserRepository
import com.app.e_commerce_app.data.repository.VariationRepository
import com.app.e_commerce_app.model.CategoryModel
import com.app.e_commerce_app.model.UserJson
import com.app.e_commerce_app.model.variation.VariationGetAllModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminVaritionViewModel @Inject constructor(
    private val varitionRepository: VariationRepository,
    private val categoryRepository: CategoryRepository
) : BaseViewModel() {

    private val _categoriesData = MutableLiveData<List<CategoryModel>>()
    val categoriesData: LiveData<List<CategoryModel>> = _categoriesData

    private val _varitionsData = MutableLiveData<List<VariationGetAllModel>>()
    val varitionsData: LiveData<List<VariationGetAllModel>> = _varitionsData

    fun fetchAllVaritions() {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val response = varitionRepository.getAllVaritions()
            _varitionsData.postValue(response!!.variations)
        }
        registerJobFinish()
    }

    fun fetchAllCategories() {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val response = categoryRepository.getAllCategories()
            _categoriesData.postValue(response!!)
        }
        registerJobFinish()
    }

}