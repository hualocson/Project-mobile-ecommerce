package com.app.e_commerce_app.ui.admin.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentAdminViewOrderBinding
import com.app.e_commerce_app.ui.admin.adapter.ViewPagerAdminAdapter
import com.app.e_commerce_app.ui.admin.user.AdminViewUserProfileFragmentArgs
import com.app.e_commerce_app.viewmodel.OrderViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment : BaseFragment<FragmentAdminViewOrderBinding>(true) {

    private val orderViewModel by activityViewModels<OrderViewModel>()
    private val args by navArgs<OrderFragmentArgs>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        val id = args.UserId
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
        binding.headerView.btnLeft.setOnClickListener {
            navigateBack()
        }
        binding.orderViewModel = orderViewModel
        if(id>0){
            orderViewModel.fetchUserOrdersAdmin(id)
        }
        else{
            orderViewModel.fetchAllUserOrdersAdmin()
        }
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAdminViewOrderBinding {
        return FragmentAdminViewOrderBinding.inflate(inflater, container, false)
    }


}
