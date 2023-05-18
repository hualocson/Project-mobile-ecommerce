package com.app.e_commerce_app.ui.admin.category

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.app.e_commerce_app.R
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentAdminEditCategoryBinding
import com.app.e_commerce_app.model.CategoryRequest
import com.app.e_commerce_app.ui.admin.adapter.VariationAdminAdapter
import com.app.e_commerce_app.ui.admin.category.viewmodel.AdminEditCategoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminEditCategoryFragment : BaseFragment<FragmentAdminEditCategoryBinding>(true) {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAdminEditCategoryBinding {
        return FragmentAdminEditCategoryBinding.inflate(inflater, container, false)
    }

    private val args by navArgs<AdminEditCategoryFragmentArgs>()
    private var isAdd = true
    private val viewModel by activityViewModels<AdminEditCategoryViewModel>()
    private val adapter: VariationAdminAdapter by lazy {
        VariationAdminAdapter(requireContext(), onEditClick, onRemoveClick)
    }

    private fun listenClickEvent() {
        binding.headerView.btnLeft.setOnClickListener {
            navigateBack()
        }

        binding.fabAdd.setOnClickListener {
            AddVariationBottomSheet().show(childFragmentManager, AddVariationBottomSheet.ADD)
        }
        if (isAdd) {
            binding.btnAdd.setOnClickListener {
                val categoryName = binding.etCategoryName.editText?.text.toString()
                val categoryIc = binding.etIcUrl.editText?.text.toString()
                val requestData = CategoryRequest(
                    categoryName, categoryIc, viewModel.items.value.orEmpty()
                )
                viewModel.addCategory(requestData)
            }
        } else {
            binding.btnAdd.setOnClickListener {
                viewModel.categoryItem.value?.let {
                    val request = CategoryRequest(
                        it.categoryName, it.categoryIc, viewModel.items.value.orEmpty()
                    )
                    viewModel.updateCategory(args.categoryItem!!.id, request)
                }
            }
        }
    }

    private fun setUpLayout() {
        binding.rvVariation.layoutManager =
            GridLayoutManager(requireContext(), 2)
        binding.rvVariation.adapter = adapter
    }


    private fun observerEvent() {
        registerAllExceptionEvent(viewModel, viewLifecycleOwner)
        registerObserverLoadingEvent(viewModel, viewLifecycleOwner)
        registerObserverNavigateEvent(viewModel, viewLifecycleOwner)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        setUpLayout()
        observerEvent()
        if (args.categoryItem != null) {
            viewModel.fetchData(args.categoryItem!!)
            binding.btnAdd.setText(R.string.update)
            isAdd = false
        }
        listenClickEvent()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearData()
    }

    private val onEditClick: (String) -> Unit = {
        AddVariationBottomSheet(it).show(childFragmentManager, AddVariationBottomSheet.EDIT)
    }
    private val onRemoveClick: (String) -> Unit = {
        viewModel.removeFromList(it)
    }
}