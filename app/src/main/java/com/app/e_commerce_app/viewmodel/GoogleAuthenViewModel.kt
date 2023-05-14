package com.app.e_commerce_app.viewmodel

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.e_commerce_app.BuildConfig
import com.app.e_commerce_app.R
import com.app.e_commerce_app.base.BaseViewModel
import com.app.e_commerce_app.data.repository.TokenRepository
import com.app.e_commerce_app.data.repository.UserRepository
import com.app.e_commerce_app.model.PreSignupRequest
import com.app.e_commerce_app.model.RegisterRequest
import com.app.e_commerce_app.utils.SignInState
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoogleAuthenViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val tokenRepository: TokenRepository
) : BaseViewModel() {
    private val _signInState = MutableLiveData<SignInState>()
    val signInState: LiveData<SignInState>
        get() = _signInState

    private lateinit var signInClient: SignInClient
    val client: SignInClient
        get() = signInClient

    fun initSignInClient(activity: Activity) {
        signInClient = Identity.getSignInClient(activity)
    }

    fun beginSignIn() : BeginSignInRequest {
        return BeginSignInRequest.builder()
            .setPasswordRequestOptions(BeginSignInRequest.PasswordRequestOptions.Builder()
                .setSupported(true)
                .build())
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.Builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(BuildConfig.WEB_CLIENT_ID)
                    // Show all accounts on the device.
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()
    }
    fun register(registerRequest: RegisterRequest) {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val res = userRepository.register(registerRequest)
            navigateToPage(R.id.loginFragment)
        }
        registerJobFinish()
    }
//
//    fun authenticate(idTokenString: String) {
//        val verifier: GoogleIdTokenVerifier = GoogleIdTokenVerifier.Builder(okHttpClient, gson)
//            // Specify the CLIENT_ID of the app that accesses the backend:
//            .setAudience(listOf(BuildConfig.WEB_CLIENT_ID))
//            .build()
//        val idToken: GoogleIdToken? = verifier.verify(idTokenString)
//        if (idToken != null) {
//            val payload: Payload = idToken.payload
//
//            // Print user identifier
//            val userId: String = payload.subject
//            println("User ID: $userId")
//
//            // Get profile information from payload
//            val email: String? = payload.email
//            val emailVerified: Boolean = payload.emailVerified?: false
//            val name: String? = payload["name"] as? String
//            val pictureUrl: String? = payload["picture"] as? String
//            val locale: String? = payload["locale"] as? String
//            val familyName: String? = payload["family_name"] as? String
//            val givenName: String? = payload["given_name"] as? String
//
//            // Use or store profile information
//            // ...
//
//        } else {
//            println("Invalid ID token.")
//        }
//    }
}