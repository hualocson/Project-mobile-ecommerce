package com.app.e_commerce_app.ui.admin.order

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.app.e_commerce_app.R
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.common.EventObserver
import com.app.e_commerce_app.databinding.FragmentAdminOrderDetailsBinding
import com.app.e_commerce_app.model.UpdateOrderRequest
import com.app.e_commerce_app.model.order.OrderLineJson
import com.app.e_commerce_app.model.product.ProductItemJson
import com.app.e_commerce_app.ui.OrderDetailsFragmentArgs
import com.app.e_commerce_app.ui.adapter.OrderDetailsAdapter
import com.app.e_commerce_app.ui.admin.adapter.OrderDetailsAdminAdapter
import com.app.e_commerce_app.ui.admin.order.viewmodel.OrderDetailsViewModel
import com.app.e_commerce_app.utils.OrderStatus
import com.app.e_commerce_app.viewmodel.OrderViewModel
import com.app.e_commerce_app.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.github.muddz.styleabletoast.StyleableToast
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OrderDetailsFragment : BaseFragment<FragmentAdminOrderDetailsBinding>(true) {

    private val orderViewModel by viewModels<OrderViewModel>()

    private val orderDetailsViewModel by viewModels<OrderDetailsViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val args by navArgs<OrderDetailsFragmentArgs>()

    private val orderDetailsAdminAdapter: OrderDetailsAdminAdapter by lazy {
        OrderDetailsAdminAdapter(requireContext(), onClick)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        val id = args.orderId
        if (id > 0) {
            binding.orderViewModel = orderViewModel
        }
        observerEvent()

        orderViewModel.getOrderById(id)
        val status = arrayOf(OrderStatus.PENDING.value, OrderStatus.PROCESSING.value, OrderStatus.CANCELLED.value, OrderStatus.DELIVERED.value)
        val langadapter = ArrayAdapter<CharSequence>(requireActivity(), R.layout.layout_spinner_text, status)
        langadapter.setDropDownViewResource(R.layout.simple_spinner_dropdown)
        binding.spinner2.adapter = langadapter
        binding.btnUpdate.setOnClickListener {
            val statusUpdate = binding.spinner2.selectedItem.toString()
            Log.d("status", statusUpdate)
            if(statusUpdate != null){
                val updateOrderRequest: UpdateOrderRequest = UpdateOrderRequest(statusUpdate)
                orderViewModel.updateOrder(id, updateOrderRequest)
                orderViewModel.updateSuccess.observe(viewLifecycleOwner, EventObserver { isSuccess ->
                    if (isSuccess) {
                        StyleableToast.makeText(requireContext(), "Update success !", Toast.LENGTH_LONG, R.style.SuccessToast).show()
                    }
                    else{
                        StyleableToast.makeText(requireContext(), "Update fail !", Toast.LENGTH_LONG, R.style.ErrorToast).show()
                    }
                })

            }
        }
        orderViewModel.activeItemData.observe(viewLifecycleOwner){
            orderDetailsViewModel.getProductItem(it.orderLines)
        }
        orderDetailsViewModel.productItemData.observe(viewLifecycleOwner){
            Log.d("data", it.toString())
            orderDetailsAdminAdapter.setData(it)
        }
        setUpRecycleView()
    }

    private fun observerEvent() {
        registerAllExceptionEvent(orderViewModel, viewLifecycleOwner)
        registerObserverLoadingEvent(orderViewModel, viewLifecycleOwner)
        registerObserverNavigateEvent(orderViewModel, viewLifecycleOwner)
    }

    private fun setUpRecycleView() {
        binding.rvOrderitems.adapter = orderDetailsAdminAdapter
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
