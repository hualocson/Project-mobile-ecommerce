package com.app.e_commerce_app.ui.admin.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentAdminOrderDetailsBinding
import com.app.e_commerce_app.model.order.OrderLineJson
import com.app.e_commerce_app.ui.OrderDetailsFragmentArgs
import com.app.e_commerce_app.ui.adapter.OrderDetailsAdapter
import com.app.e_commerce_app.viewmodel.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderDetailsFragment : BaseFragment<FragmentAdminOrderDetailsBinding>(true) {

    private val orderViewModel by viewModels<OrderViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val args by navArgs<OrderDetailsFragmentArgs>()

    private val orderDetailsAdapter: OrderDetailsAdapter by lazy {
        OrderDetailsAdapter(requireContext(), onClick)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        val id = args.orderId
        if (id > 0) {
            binding.orderViewModel = orderViewModel
        }
        observerEvent()
        setUpRecycleView()
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
    ): FragmentAdminOrderDetailsBinding {
        return FragmentAdminOrderDetailsBinding.inflate(inflater, container, false)
    }

    private val onClick: (OrderLineJson) -> Unit = {

    }

}
