package com.app.e_commerce_app.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.e_commerce_app.ui.OrderCommonFragment
import com.app.e_commerce_app.ui.OrderCompleteFragment
import com.bumptech.glide.manager.Lifecycle

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: androidx.lifecycle.Lifecycle): FragmentStateAdapter(fragmentManager,lifecycle){
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->{
                OrderCommonFragment()
            }
            1->{
                OrderCompleteFragment()
            }
            else->{
                Fragment()
            }
        }
    }

}