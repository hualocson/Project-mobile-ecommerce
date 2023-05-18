package com.app.e_commerce_app.ui.admin.sales

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.GridLayoutManager
import com.app.e_commerce_app.R
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentNewsBinding
import com.app.e_commerce_app.databinding.FragmentSalesAdminBinding
import com.app.e_commerce_app.model.SaleJson
import com.app.e_commerce_app.ui.adapter.SaleAdapter
import com.app.e_commerce_app.ui.admin.adapter.SaleAdminAdapter
import com.app.e_commerce_app.viewmodel.SaleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SaleAdminFragment : BaseFragment<FragmentSalesAdminBinding>(true) {

    private val saleViewModel by viewModels<SaleViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val saleAdminAdapter: SaleAdminAdapter by lazy{
        SaleAdminAdapter(requireContext(), onItemClick)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.newViewModel = saleViewModel
        observerEvent()
        setUpRecycleView()
        saleViewModel.getAllNews()
        binding.btnAdd.setOnClickListener {
            navigateToPage(R.id.action_adminSalesFragment_to_adminAddSalesFragment)
        }
    }

    private fun observerEvent() {
        registerAllExceptionEvent(saleViewModel, viewLifecycleOwner)
        registerObserverLoadingEvent(saleViewModel, viewLifecycleOwner)
        registerObserverNavigateEvent(saleViewModel, viewLifecycleOwner)
    }

    private fun setUpRecycleView() {
        binding.rvSales.adapter = saleAdminAdapter
        binding.rvSales.layoutManager = GridLayoutManager(context, 1)
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSalesAdminBinding {
        return FragmentSalesAdminBinding.inflate(inflater, container, false)
    }

    private val onItemClick: (SaleJson) -> Unit = {
        val action: NavDirections = SaleAdminFragmentDirections.actionAdminSalesFragmentToAdminEditSalesFragment(it)
        navigateAction(action)
    }

}
