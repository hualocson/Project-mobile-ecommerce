package com.app.e_commerce_app.ui.admin.varition

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.e_commerce_app.R
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentAdminProductBinding
import com.app.e_commerce_app.databinding.FragmentAdminVaritionBinding
import com.app.e_commerce_app.model.CategoryModel
import com.app.e_commerce_app.model.ChooseItem
import com.app.e_commerce_app.model.variation.VariationGetAllModel
import com.app.e_commerce_app.ui.admin.adapter.ItemAdminAdapter
import com.app.e_commerce_app.ui.admin.adapter.VaritionAllAdminAdapter
import com.app.e_commerce_app.ui.admin.products.viewmodel.AdminProductViewModel
import com.app.e_commerce_app.ui.admin.varition.viewmodel.AdminVaritionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminVaritionFragment : BaseFragment<FragmentAdminVaritionBinding>(true) {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAdminVaritionBinding {
        return FragmentAdminVaritionBinding.inflate(inflater, container, false)
    }

    private val viewModel by viewModels<AdminVaritionViewModel>()

    private val adapter: VaritionAllAdminAdapter by lazy {
        VaritionAllAdminAdapter(requireContext(), onClick)
    }

    private fun listenClickEvent() {
        binding.headerView.btnLeft.setOnClickListener {
            navigateBack()
        }

        binding.btnAdd.setOnClickListener {
            navigateToPage(R.id.action_adminProductFragment_to_adminEditProductFragment)
        }
    }
    private fun observerEvent() {
        registerAllExceptionEvent(viewModel, viewLifecycleOwner)
        registerObserverLoadingEvent(viewModel, viewLifecycleOwner)
        registerObserverNavigateEvent(viewModel, viewLifecycleOwner)
    }


    private fun setUpLayout() {
        binding.rvVarition.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvVarition.adapter = adapter
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        viewModel.fetchAllCategories()
        viewModel.fetchAllVaritions()
        viewModel.varitionsData.observe(viewLifecycleOwner){
            Log.d("data", it.toString())
            adapter.setVaritionsData(it)
        }
        setUpLayout()
        observerEvent()
        listenClickEvent()
    }

    private val onClick: (CategoryModel) -> Unit = {
    }
}