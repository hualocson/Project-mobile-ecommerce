package com.app.e_commerce_app.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentWelcomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeFragment: BaseFragment<FragmentWelcomeBinding>(true){
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWelcomeBinding {
        return FragmentWelcomeBinding.inflate(inflater, container, false)
    }
}