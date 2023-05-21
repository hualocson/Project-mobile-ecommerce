package com.app.e_commerce_app.ui.admin.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Index
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentAdminOrderProcessBinding
import com.app.e_commerce_app.databinding.FragmentNewsBinding
import com.app.e_commerce_app.databinding.FragmentOrderBinding
import com.app.e_commerce_app.databinding.FragmentOrderCompleteBinding
import com.app.e_commerce_app.model.SaleJson
import com.app.e_commerce_app.model.order.OrderJson
import com.app.e_commerce_app.ui.adapter.OrderAdapter
import com.app.e_commerce_app.ui.adapter.SaleAdapter
import com.app.e_commerce_app.ui.admin.adapter.OrderAdminAdapter
import com.app.e_commerce_app.viewmodel.OrderViewModel
import com.app.e_commerce_app.viewmodel.SaleViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class OrderProcessFragment : BaseFragment<FragmentAdminOrderProcessBinding>(true) {

    private val orderViewModel by activityViewModels<OrderViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val orderAdminAdapter: OrderAdminAdapter by lazy{
        OrderAdminAdapter(requireContext(), onClick)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.orderViewModel = orderViewModel
        observerEvent()
        setUpRecycleView()
//        orderViewModel.fetchAllUserOrders()
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterList(newText.lowercase(Locale.ROOT))
                return false
            }
        })
    }

    private fun observerEvent() {
        registerAllExceptionEvent(orderViewModel, viewLifecycleOwner)
        registerObserverLoadingEvent(orderViewModel, viewLifecycleOwner)
        registerObserverNavigateEvent(orderViewModel, viewLifecycleOwner)
    }

    private fun setUpRecycleView() {
        binding.rvcorderprocess.adapter = orderAdminAdapter
        binding.rvcorderprocess.layoutManager = GridLayoutManager(context, 1)
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAdminOrderProcessBinding {
        return FragmentAdminOrderProcessBinding.inflate(inflater, container, false)
    }

    private val onClick: (OrderJson) -> Unit = {
        val action: NavDirections = OrderFragmentDirections.actionUpdateOrderFragmentToOrderAdminDetailsFragment(it.id)
        navigateAction(action)
    }
    private fun filterList(newText: String) {
        val orderList: ArrayList<OrderJson> = ArrayList()
        orderViewModel.orderProcessData.value!!.forEach { item ->
            if (item.orderLines.get(0).name.lowercase(Locale.ROOT).contains(newText)) {
                orderList.add(item)
            }
        }
        orderAdminAdapter.setFilterList(orderList)
    }
}
