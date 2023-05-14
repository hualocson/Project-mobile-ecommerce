package com.app.e_commerce_app.ui

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.app.e_commerce_app.R
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.base.BaseViewModel
import com.app.e_commerce_app.common.EventObserver
import com.app.e_commerce_app.databinding.FragmentSplashBinding
import com.app.e_commerce_app.viewmodel.HomeViewModel

class SplashFragment : BaseFragment<FragmentSplashBinding>(true) {

    private val homeViewModel: HomeViewModel by activityViewModels()

    private fun observerEvent() {
        registerAllExceptionEvent(homeViewModel, viewLifecycleOwner)
        registerObserverNavigateEvent(homeViewModel, viewLifecycleOwner)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.homeViewModel = homeViewModel
        observerEvent()

        if (!homeViewModel.checkIsLogin())
            navigateToPage(R.id.action_homeFragment_to_loginFragment)
        else {
            homeViewModel.fetchUser()
            homeViewModel.fetchData()
        }

        homeViewModel.isFetchDataSuccess.observe(viewLifecycleOwner, EventObserver{success ->
            if(success)
                navigateToPage(R.id.action_splashFragment_to_homeFragment)
        })
    }
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSplashBinding {
        return FragmentSplashBinding.inflate(inflater, container, false)
    }
}