package com.app.e_commerce_app.ui.admin.shipping

import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentAdminShippingUpdateBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminShippingUpdateFragment : BaseFragment<FragmentAdminShippingUpdateBinding>(true) {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAdminShippingUpdateBinding {
        return FragmentAdminShippingUpdateBinding.inflate(inflater, container, false)
    }

}