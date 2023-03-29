package com.app.e_commerce_app.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.data.repository.UserRepository
import com.app.e_commerce_app.model.LoginRequest
import com.app.e_commerce_app.utils.Resource
import kotlinx.coroutines.Dispatchers

class UserViewModel(application: Application) : ViewModel() {

    private val userRepository: UserRepository = UserRepository();

    fun login(loginRequest: LoginRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))

        when (val response = userRepository.login(loginRequest)) {
            is NetWorkResult.Success -> emit(Resource.success(response.data))
            is NetWorkResult.Error -> emit(Resource.error(null, response.message))
            is NetWorkResult.Exception -> emit(Resource.error(null, response.e.message))
        }
    }

    class UserViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return UserViewModel(application) as T
            }
            throw IllegalArgumentException("Unable construct viewModel")
        }
    }
}