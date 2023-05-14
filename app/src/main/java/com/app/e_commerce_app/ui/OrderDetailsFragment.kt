package com.app.e_commerce_app.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentNewsBinding
import com.app.e_commerce_app.databinding.FragmentOrderDetailsBinding
import com.app.e_commerce_app.model.SaleJson
import com.app.e_commerce_app.model.order.OrderJson
import com.app.e_commerce_app.model.order.OrderLineJson
import com.app.e_commerce_app.ui.adapter.OrderAdapter
import com.app.e_commerce_app.ui.adapter.OrderDetailsAdapter
import com.app.e_commerce_app.ui.adapter.SaleAdapter
import com.app.e_commerce_app.viewmodel.OrderViewModel
import com.app.e_commerce_app.viewmodel.SaleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderDetailsFragment : BaseFragment<FragmentOrderDetailsBinding>(true) {

    private val orderViewModel by viewModels<OrderViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val orderDetailsAdapter: OrderDetailsAdapter by lazy{
        OrderDetailsAdapter(requireContext(), onClick)
    }

    private val args by navArgs<OrderDetailsFragmentArgs>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        val id = args.orderId
        if(id > 0){
            binding.orderViewModel = orderViewModel
        }
        observerEvent()
        setUpRecycleView()
        Log.d("orderId", id.toString())
        orderViewModel.getOrderById(id)
    }

    private fun observerEvent() {
        registerAllExceptionEvent(orderViewModel, viewLifecycleOwner)
        registerObserverLoadingEvent(orderViewModel, viewLifecycleOwner)
        registerObserverNavigateEvent(orderViewModel, viewLifecycleOwner)
    }

    private fun setUpRecycleView() {
        binding.rvOrderitems.adapter = orderDetailsAdapter
        binding.rvOrderitems.layoutManager = GridLayoutManager(context, 1)
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOrderDetailsBinding {
        return FragmentOrderDetailsBinding.inflate(inflater, container, false)
    }
    private val onClick: (OrderLineJson) -> Unit = {

    }

}
