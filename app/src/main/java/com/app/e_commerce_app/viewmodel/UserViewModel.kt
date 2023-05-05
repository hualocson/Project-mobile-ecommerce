package com.app.e_commerce_app.viewmodel

import android.app.Application
import android.app.BackgroundServiceStartNotAllowedException
import android.media.session.MediaSession.Token
import android.util.Log
import androidx.lifecycle.*
import com.app.e_commerce_app.R
import com.app.e_commerce_app.base.BaseViewModel
import com.app.e_commerce_app.common.AppSharePreference
import com.app.e_commerce_app.common.Event
import com.app.e_commerce_app.data.repository.TokenRepository
import com.app.e_commerce_app.data.repository.UserRepository
import com.app.e_commerce_app.data.services.UserRemoteService
import com.app.e_commerce_app.model.LoginRequest
import com.app.e_commerce_app.model.PreSignupRequest
import com.app.e_commerce_app.model.RegisterRequest
import com.app.e_commerce_app.model.UserJson
import com.app.e_commerce_app.model.token.TokenModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : BaseViewModel() {

    private val userRemoteService = UserRemoteService()

    private val userRepository = UserRepository(userRemoteService)
    private val tokenRepository = TokenRepository(AppSharePreference(application))

    private val _userLiveData = MutableLiveData<UserJson>()
    val userLiveData: LiveData<UserJson> = _userLiveData


        fun checkEmail(preSignupRequest: PreSignupRequest) {
            showLoading(true)
            parentJob = viewModelScope.launch(handler) {
                val res = userRepository.checkEmail(preSignupRequest)
            }
            registerJobFinish()
        }
//    fun checkEmail(preSignUpRequest : PreSignupRequest) = liveData(Dispatchers.IO)
//    {
//        emit(Resource.loading(null))
//
//        when (val response = userRepository.checkEmail(preSignUpRequest)) {
//            is NetWorkResult.Success -> {
//                response.data.data.let {
//                    emit(Resource.success(it))
//                }
//            }
//            is NetWorkResult.Error -> emit(Resource.error(null, response.message))
//            is NetWorkResult.Exception -> emit(Resource.error(null, response.e.message))
//        }
//    }

    fun register(registerRequest: RegisterRequest) {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val res = userRepository.register(registerRequest)
        }
        registerJobFinish()
    }

//    fun register(registerRequest: RegisterRequest) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(null))
//
//        when (val response = userRepository.register(registerRequest)) {
//            is NetWorkResult.Success -> {
//                response.data.data.let { userJson ->
//                    emit(Resource.success(userJson))
//                }
//            }
//            is NetWorkResult.Error -> emit(Resource.error(null, response.message))
//            is NetWorkResult.Exception -> emit(Resource.error(null, response.e.message))
//        }
//    }

    fun login(loginRequest: LoginRequest) {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val token = userRepository.login(loginRequest)
            if(token.accessToken.isNotEmpty() or token.refreshToken.isNotEmpty())
            {
                tokenRepository.saveToken(token)
                navigateToPage(R.id.action_loginFragment_to_homeFragment)
            }
        }
        registerJobFinish()
    }

    fun fetchUser() {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val user = userRepository.getUserProfile()
            Log.d("USer", user.toString())
            _userLiveData.postValue(user)
        }
        registerJobFinish()
    }

//    fun loadUserProfile() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(null))
//
//        when (val response = userRepository.getUserProfile()) {
//            is NetWorkResult.Success -> {
//                response.data.data.let { user ->
//                    emit(Resource.success(user))
//                    _userLiveData.postValue(user)
//                }
//            }
//            is NetWorkResult.Error -> emit(Resource.error(null, response.message))
//            is NetWorkResult.Exception -> emit(Resource.error(null, response.e.message))
//        }
//    }
//
    fun setRemember(remember: Boolean) {
        tokenRepository.setRemember(remember)
    }

    fun checkIsLogin() : Boolean {
        return tokenRepository.checkIsLogin()
    }
//
//    fun fetchUser() {
//        showLoading(true)
//        parentJob = viewModelScope.launch(Dispatchers.IO) {
//            val response = userRepository.getUserProfile()
//            if (response is NetWorkResult.Success) {
//                _userLiveData.postValue(response.data.data!!)
//            }
//        }
//        registerJobFinish()
//    }

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