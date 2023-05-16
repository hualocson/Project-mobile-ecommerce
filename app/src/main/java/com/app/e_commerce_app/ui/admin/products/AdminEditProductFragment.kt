package com.app.e_commerce_app.ui.admin.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.navArgs
import com.app.e_commerce_app.R
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.common.EventObserver
import com.app.e_commerce_app.databinding.FragmentAdminEditProductBinding
import com.app.e_commerce_app.model.product.ProductRequest
import com.app.e_commerce_app.ui.admin.adapter.CustomDropdownAdapter
import com.app.e_commerce_app.ui.admin.products.viewmodel.AdminEditProductViewModel
import com.app.e_commerce_app.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminEditProductFragment : BaseFragment<FragmentAdminEditProductBinding>(true) {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAdminEditProductBinding {
        return FragmentAdminEditProductBinding.inflate(inflater, container, false)
    }

    private var activeCategory = 0
    private val viewModel by viewModels<AdminEditProductViewModel>()
    private val args by navArgs<AdminEditProductFragmentArgs>()
    private val adapter by lazy {
        CustomDropdownAdapter(requireContext(), R.layout.item_custom_dropdown)
    }

    private fun listenClickEvent() {
        binding.headerView.btnLeft.setOnClickListener {
            navigateBack()
        }

        binding.btnAdd.setOnClickListener {
            if (args.productId != 0) {
                // update
                val data = ProductRequest.getUpdateData(
                    binding.etName.editText?.text.toString(),
                    binding.etDesc.editText?.text.toString(),
                    binding.etImg.editText?.text.toString()
                )
                if (data.isUpdateDataValid)
                    viewModel.updateProduct(args.productId, data)
            } else {
//                binding.dropItems.setOnItemClickListener { parent, view, position, id ->
//                    val selectedItem = parent.getItemAtPosition(position).toString()
//                    autoCompleteTextView.setText(selectedItem, false)
//                }

                val data = ProductRequest.getCreateDate(
                    activeCategory,
                    binding.etName.editText?.text.toString(),
                    binding.etDesc.editText?.text.toString(),
                    binding.etImg.editText?.text.toString()
                )
                // add
                if (data.isCreateDataValid)
                    viewModel.addProduct(data)
                else
                    showNotify("Warning!!", "Please check your input data!!", Status.WARNING)
            }
        }

        binding.btnViewProductItem.setOnClickListener {
            val action : NavDirections = AdminEditProductFragmentDirections.actionAdminEditProductFragmentToAdminProductItem(args.productId)
            navigateAction(action)
        }
    }

    private fun observerEvent() {
        registerAllExceptionEvent(viewModel, viewLifecycleOwner)
        registerObserverLoadingEvent(viewModel, viewLifecycleOwner)
        registerObserverNavigateEvent(viewModel, viewLifecycleOwner)
    }


    private fun prepareData() {
        val productId = args.productId
        if (productId == 0) {
            viewModel.fetchAllCategories()
            binding.btnViewProductItem.visibility = View.GONE
        } else {
            viewModel.getProductById(productId)
            binding.btnAdd.setText(R.string.update)
            binding.categoryMenu.isEnabled = false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        observerEvent()
        prepareData()
        listenClickEvent()

        viewModel.categoriesData.observe(viewLifecycleOwner) {
            it?.let {
                val data = it.map { category ->
                    Pair(category.id, category.categoryName)
                }
                adapter.setItems(data)
                val autoCompleteTextView =
                    (binding.categoryMenu.editText as? AutoCompleteTextView)
                autoCompleteTextView?.setAdapter(adapter)
                if (args.productId != 0) {
                    autoCompleteTextView?.setText(adapter.getItem(0)!!.second, false)
                }
            }
        }

        viewModel.isResponseSuccess.observe(viewLifecycleOwner, EventObserver { isSuccess ->
            if (isSuccess)
                navigateBack()
        })

        val autoCompleteTextView: AutoCompleteTextView =
            binding.categoryMenu.editText as AutoCompleteTextView

        autoCompleteTextView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                val selectedPair = parent.getItemAtPosition(position) as? Pair<*, *>
                selectedPair?.let {
                    val selectedString: String = selectedPair.second as String
                    activeCategory = selectedPair.first as Int
                    autoCompleteTextView.setText(selectedString, false)
                }
            }
    }
}