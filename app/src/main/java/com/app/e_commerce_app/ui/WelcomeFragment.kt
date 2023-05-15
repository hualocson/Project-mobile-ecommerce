package com.app.e_commerce_app.ui

import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.app.e_commerce_app.R
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentWelcomeBinding
import com.app.e_commerce_app.model.RegisterRequest
import com.app.e_commerce_app.viewmodel.GoogleAuthenViewModel
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(true) {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWelcomeBinding {
        return FragmentWelcomeBinding.inflate(inflater, container, false)
    }
    private val googleViewModel by viewModels<GoogleAuthenViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        observerEvent()
        binding.btnGoogle.setOnClickListener {
            Log.d("btn click", "clickedddddddddddd")
            displaySignIn()
        }

        binding.btnLogin.setOnClickListener {
            navigateToPage(R.id.action_welcomeFragment_to_loginFragment)
        }
        binding.tvSignup.setOnClickListener {
            navigateToPage(R.id.action_welcomeFragment_to_signupFragment)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        googleViewModel.initSignInClient(requireActivity())
    }
    private fun observerEvent() {
        registerAllExceptionEvent(googleViewModel, viewLifecycleOwner)
        registerObserverLoadingEvent(googleViewModel, viewLifecycleOwner)
        registerObserverNavigateEvent(googleViewModel, viewLifecycleOwner)
    }
    private fun displaySignIn(){
        googleViewModel.client.beginSignIn(googleViewModel.beginSignIn())
            .addOnSuccessListener(requireActivity()) { result ->
                try {
                    val ib = IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                    oneTapResult.launch(ib)
                } catch (e: IntentSender.SendIntentException) {
                    Log.e("btn click", "Couldn't start One Tap UI: ${e.localizedMessage}")
                }
            }
            ?.addOnFailureListener(requireActivity()) { e ->
                // No Google Accounts found. Just continue presenting the signed-out UI.
//                displaySignUp()
                Log.d("btn click failed", e.localizedMessage!!)
            }
    }


    private val oneTapResult = registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()){ result ->
        try {
            val credential = googleViewModel.client.getSignInCredentialFromIntent(result.data)
            val idToken = credential.googleIdToken
            val email = credential.id
            val profilePictureUri = credential.profilePictureUri
            val givenName = credential.givenName
            val familyName = credential.familyName
            when {
                idToken != null -> {
                    // Got an ID token from Google. Use it to authenticate
                    // with your backend.
                    val msg = "idToken: $idToken"
                    googleViewModel.register(RegisterRequest(email.toString(), email.toString(),profilePictureUri.toString(),
                        givenName.toString(),
                        familyName.toString(), "000000"))
                }
                else -> {
                    // Shouldn't happen.
                    Log.d("one tap", "No ID token!")
                }
            }
        } catch (e: ApiException) {
            when (e.statusCode) {
                CommonStatusCodes.CANCELED -> {
                    Log.d("one tap", "One-tap dialog was closed.")
                    // Don't re-prompt the user.
                 }
                CommonStatusCodes.NETWORK_ERROR -> {
                    Log.d("one tap", "One-tap encountered a network error.")
                    // Try again or just ignore.
                }
                else -> {
                    Log.d("one tap", "Couldn't get credential from result." +
                            " (${e.localizedMessage})")
                 }
            }
        }
    }
}