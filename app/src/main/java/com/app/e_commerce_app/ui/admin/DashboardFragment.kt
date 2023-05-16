package com.app.e_commerce_app.ui.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.e_commerce_app.R
import androidx.navigation.NavDirections
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentAdminDashboardBinding
import com.app.e_commerce_app.ui.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class DashboardFragment : BaseFragment<FragmentAdminDashboardBinding>(true) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.btnUpdateOrder.setOnClickListener {
            val action: NavDirections = DashboardFragmentDirections.actionDashboardFragmentToUpdateOrderFragment()
            navigateAction(action)
        }
    }
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAdminDashboardBinding {
        return FragmentAdminDashboardBinding.inflate(inflater, container, false)
    }

    private fun listenClickEvent() {
        binding.headerView.btnLeft.setOnClickListener {
            navigateBack()
        }
        binding.btnProduct.setOnClickListener {
            navigateToPage(R.id.action_dashboardFragment_to_adminProductFragment)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listenClickEvent()
    }
}