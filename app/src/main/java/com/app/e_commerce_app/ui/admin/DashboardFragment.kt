package com.app.e_commerce_app.ui.admin

import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentAdminDashboardBinding

class DashboardFragment : BaseFragment<FragmentAdminDashboardBinding>(true) {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAdminDashboardBinding {
        return FragmentAdminDashboardBinding.inflate(inflater, container, false)
    }
}