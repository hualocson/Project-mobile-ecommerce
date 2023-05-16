package com.app.e_commerce_app.ui.admin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.GridLayoutManager
import com.app.e_commerce_app.R
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentAdminViewOrderBinding
import com.app.e_commerce_app.databinding.FragmentNewsBinding
import com.app.e_commerce_app.databinding.FragmentOrderBinding
import com.app.e_commerce_app.model.SaleJson
import com.app.e_commerce_app.ui.adapter.SaleAdapter
import com.app.e_commerce_app.ui.adapter.ViewPagerAdapter
import com.app.e_commerce_app.ui.adapter.admin.ViewPagerAdminAdapter
import com.app.e_commerce_app.viewmodel.OrderViewModel
import com.app.e_commerce_app.viewmodel.SaleViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment : BaseFragment<FragmentAdminViewOrderBinding>(false) {

    private val orderViewModel by activityViewModels<OrderViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        val adapter = ViewPagerAdminAdapter(requireActivity().supportFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager){tab, position ->
            when(position){
                0->{
                    tab.text = "Pending"
                }
                1->{
                   tab.text = "Process"
                }
                2->{
                    tab.text = "Complete"
                }
                3->{
                    tab.text = "Cancel"
                }
            }
        }.attach()
        binding.orderViewModel = orderViewModel
        orderViewModel.fetchAllUserOrdersAdmin()
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAdminViewOrderBinding {
        return FragmentAdminViewOrderBinding.inflate(inflater, container, false)
    }


}
