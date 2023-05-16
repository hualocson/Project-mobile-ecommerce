package com.app.e_commerce_app.ui.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import com.app.e_commerce_app.R
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentAdminDashboardBinding

class DashboardFragment : BaseFragment<FragmentAdminDashboardBinding>(true) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        listenClickEvent()
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
        binding.btnUpdateOrder.setOnClickListener {
            val action: NavDirections =
                DashboardFragmentDirections.actionDashboardFragmentToUpdateOrderFragment()
            navigateAction(action)
        }
    }
}