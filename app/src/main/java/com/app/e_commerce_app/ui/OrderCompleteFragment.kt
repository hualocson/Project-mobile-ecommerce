package com.app.e_commerce_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.GridLayoutManager
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentNewsBinding
import com.app.e_commerce_app.databinding.FragmentOrderBinding
import com.app.e_commerce_app.databinding.FragmentOrderCompleteBinding
import com.app.e_commerce_app.model.SaleJson
import com.app.e_commerce_app.ui.adapter.SaleAdapter
import com.app.e_commerce_app.viewmodel.SaleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderCompleteFragment : BaseFragment<FragmentOrderCompleteBinding>(true) {

    private val orderViewModel by viewModels<SaleViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
    }

//    private fun observerEvent() {
//        registerAllExceptionEvent(saleViewModel, viewLifecycleOwner)
//        registerObserverLoadingEvent(saleViewModel, viewLifecycleOwner)
//        registerObserverNavigateEvent(saleViewModel, viewLifecycleOwner)
//    }

//    private fun setUpRecycleView() {
//        binding.rvNews.adapter = saleAdapter
//        binding.rvNews.layoutManager = GridLayoutManager(context, 1)
//    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOrderCompleteBinding {
        return FragmentOrderCompleteBinding.inflate(inflater, container, false)
    }


}
