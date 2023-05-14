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
import com.app.e_commerce_app.model.SaleJson
import com.app.e_commerce_app.ui.adapter.SaleAdapter
import com.app.e_commerce_app.viewmodel.SaleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SaleFragment : BaseFragment<FragmentNewsBinding>(false) {

    private val saleViewModel by viewModels<SaleViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val saleAdapter: SaleAdapter by lazy{
        SaleAdapter(requireContext(), onItemClick)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.newViewModel = saleViewModel
        observerEvent()
        setUpRecycleView()
        saleViewModel.getAllNews()
    }

    private fun observerEvent() {
        registerAllExceptionEvent(saleViewModel, viewLifecycleOwner)
        registerObserverLoadingEvent(saleViewModel, viewLifecycleOwner)
        registerObserverNavigateEvent(saleViewModel, viewLifecycleOwner)
    }

    private fun setUpRecycleView() {
        binding.rvNews.adapter = saleAdapter
        binding.rvNews.layoutManager = GridLayoutManager(context, 1)
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNewsBinding {
        return FragmentNewsBinding.inflate(inflater, container, false)
    }

    private val onItemClick: (SaleJson) -> Unit = {
        val action: NavDirections = SaleFragmentDirections.actionSaleFragmentToSaleDetailsFragment(it)
        navigateAction(action)
    }

}
