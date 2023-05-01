package com.app.e_commerce_app.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.app.e_commerce_app.common.AppSharePreference
import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.data.repository.TokenRepository
import com.app.e_commerce_app.data.repository.UserRepository
import com.app.e_commerce_app.model.LoginRequest
import com.app.e_commerce_app.model.UserJson
import com.app.e_commerce_app.utils.Resource
import com.app.e_commerce_app.utils.Status
import kotlinx.coroutines.Dispatchers

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository = UserRepository()
    private val tokenRepository = TokenRepository(AppSharePreference(application))

    private val _userLiveData = MutableLiveData<UserJson>()
    val userLiveData: LiveData<UserJson> = _userLiveData

    fun login(loginRequest: LoginRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))

        when (val response = userRepository.login(loginRequest)) {
            is NetWorkResult.Success -> {
                response.data.data.let { tokenJson ->
                    tokenRepository.saveToken(tokenJson!!.toTokenModel())
                    val userResponse = userRepository.getUserProfile()
                    if (userResponse is NetWorkResult.Success) {
                        val userJson = userResponse.data.data!!
                        _userLiveData.postValue(userJson)
                    }
                    emit(Resource.success(tokenJson.toTokenModel()))
                }
            }
            is NetWorkResult.Error -> emit(Resource.error(null, response.message))
            is NetWorkResult.Exception -> emit(Resource.error(null, response.e.message))
        }
    }

    fun loadUserProfile() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))

        when (val response = userRepository.getUserProfile()) {
            is NetWorkResult.Success -> {
                response.data.data.let { user ->
                    _userLiveData.postValue(user)
                    emit(Resource.success(user))
                }
            }
            is NetWorkResult.Error -> emit(Resource.error(null, response.message))
            is NetWorkResult.Exception -> emit(Resource.error(null, response.e.message))
        }
    }

    fun setRemember(remember: Boolean) {
        tokenRepository.setRemember(remember)
    }

    class UserViewModelFactory(
        private val application: Application
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return UserViewModel(application) as T
            }
            throw IllegalArgumentException("Unable construct viewModel")
        }
    }
}