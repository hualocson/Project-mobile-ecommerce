package com.app.e_commerce_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Index
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentNewsBinding
import com.app.e_commerce_app.databinding.FragmentOrderBinding
import com.app.e_commerce_app.databinding.FragmentOrderCompleteBinding
import com.app.e_commerce_app.model.SaleJson
import com.app.e_commerce_app.ui.adapter.OrderAdapter
import com.app.e_commerce_app.ui.adapter.SaleAdapter
import com.app.e_commerce_app.viewmodel.OrderViewModel
import com.app.e_commerce_app.viewmodel.SaleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderCompleteFragment : BaseFragment<FragmentOrderCompleteBinding>(false) {

    private val orderViewModel by activityViewModels<OrderViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val orderAdapter: OrderAdapter by lazy{
        OrderAdapter(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.orderViewModel = orderViewModel
        observerEvent()
        setUpRecycleView()
//        orderViewModel.fetchAllUserOrders()
    }

    private fun observerEvent() {
        registerAllExceptionEvent(orderViewModel, viewLifecycleOwner)
        registerObserverLoadingEvent(orderViewModel, viewLifecycleOwner)
        registerObserverNavigateEvent(orderViewModel, viewLifecycleOwner)
    }

    private fun setUpRecycleView() {
        binding.rvordercomplete.adapter = orderAdapter
        binding.rvordercomplete.layoutManager = GridLayoutManager(context, 1)
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOrderCompleteBinding {
        return FragmentOrderCompleteBinding.inflate(inflater, container, false)
    }


}
