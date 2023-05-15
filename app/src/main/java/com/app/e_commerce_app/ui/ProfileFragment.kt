package com.app.e_commerce_app.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import com.app.e_commerce_app.R
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentProfileBinding
import com.app.e_commerce_app.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(false) {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater, container, false)
    }

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.userViewModel = userViewModel

        registerAllExceptionEvent(userViewModel, viewLifecycleOwner)
        registerObserverLoadingEvent(userViewModel, viewLifecycleOwner)
        registerObserverNavigateEvent(userViewModel, viewLifecycleOwner)

        userViewModel.fetchUser()

        binding.btnLogout.setOnClickListener {
            userViewModel.logout()
            navigateToPage(R.id.action_profileFragment_to_welcomeFragment)
        }

        binding.btnAddress.setOnClickListener {
            navigateToPage(R.id.action_profileFragment_to_addressFragment)
        }
        binding.btnUpload.setOnClickListener {
            navigateToPage(R.id.action_profileFragment_to_uploadFragment)
        }
        binding.btnEditProfile.setOnClickListener {
            val action: NavDirections = ProfileFragmentDirections.actionProfileFragmentToFillProfileFragment(
                isLogged = true
            )
            navigateAction(action)
        }
        binding.btnChangePassword.setOnClickListener {
            val action: NavDirections = ProfileFragmentDirections.actionProfileFragmentToChangePasswordFragment()
            navigateAction(action)
        }
        binding.btnManager.setOnClickListener {
            navigateToPage(R.id.action_profileFragment_to_dashboardFragment)
        }
    }
}