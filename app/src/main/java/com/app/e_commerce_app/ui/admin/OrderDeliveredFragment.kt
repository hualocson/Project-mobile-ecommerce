package com.app.e_commerce_app.ui.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.GridLayoutManager
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentAdminOrderCompleteBinding
import com.app.e_commerce_app.model.order.OrderJson
import com.app.e_commerce_app.ui.adapter.OrderAdapter
import com.app.e_commerce_app.viewmodel.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderDeliveredFragment : BaseFragment<FragmentAdminOrderCompleteBinding>(false) {

    private val orderViewModel by activityViewModels<OrderViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val orderAdapter: OrderAdapter by lazy{
        OrderAdapter(requireContext(), onClick)
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
        binding.rvOrderComplete.adapter = orderAdapter
        binding.rvOrderComplete.layoutManager = GridLayoutManager(context, 1)
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAdminOrderCompleteBinding {
        return FragmentAdminOrderCompleteBinding.inflate(inflater, container, false)
    }

    private val onClick: (OrderJson) -> Unit = {
        val action: NavDirections = OrderFragmentDirections.actionUpdateOrderFragmentToOrderAdminDetailsFragment(it.id)
        navigateAction(action)
    }
}
