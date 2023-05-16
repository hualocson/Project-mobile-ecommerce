package com.app.e_commerce_app.ui.admin.products

import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentAdminEditProductBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminEditProductFragment : BaseFragment<FragmentAdminEditProductBinding>(true) {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAdminEditProductBinding {
        return FragmentAdminEditProductBinding.inflate(inflater, container, false)
    }

}