package com.app.e_commerce_app.ui.adapter.admin

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.e_commerce_app.ui.admin.OrderCancelFragment
import com.app.e_commerce_app.ui.admin.OrderDeliveredFragment
import com.app.e_commerce_app.ui.admin.OrderPendingFragment
import com.app.e_commerce_app.ui.admin.OrderProcessFragment

class ViewPagerAdminAdapter(fragmentManager: FragmentManager, lifecycle: androidx.lifecycle.Lifecycle): FragmentStateAdapter(fragmentManager,lifecycle){
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->{
                OrderPendingFragment()
            }
            1->{
                OrderProcessFragment()
            }
            2->{
                OrderDeliveredFragment()
            }
            3->{
                OrderCancelFragment()
            }
            else->{
                Fragment()
            }
        }
    }

}