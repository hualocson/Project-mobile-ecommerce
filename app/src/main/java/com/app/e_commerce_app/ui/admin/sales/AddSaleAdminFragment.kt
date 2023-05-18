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
import com.app.e_commerce_app.databinding.FragmentAdminAddSalesBinding
import com.app.e_commerce_app.databinding.FragmentNewsBinding
import com.app.e_commerce_app.databinding.FragmentSalesAdminBinding
import com.app.e_commerce_app.model.ChangePasswordRequest
import com.app.e_commerce_app.model.SaleJson
import com.app.e_commerce_app.model.SalesRequest
import com.app.e_commerce_app.ui.ChangePasswordFragmentDirections
import com.app.e_commerce_app.ui.adapter.SaleAdapter
import com.app.e_commerce_app.ui.admin.adapter.SaleAdminAdapter
import com.app.e_commerce_app.viewmodel.SaleViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.github.muddz.styleabletoast.StyleableToast

@AndroidEntryPoint
class AddSaleAdminFragment : BaseFragment<FragmentAdminAddSalesBinding>(true) {

    private val saleViewModel by viewModels<SaleViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        observerEvent()
        binding.btnAdd.setOnClickListener {
            //Check null for all fields
            if (binding.fillTitle.text.toString().isEmpty() || binding.fillAuthor.text.toString()
                    .isEmpty() || binding.fillImg.text.toString().isEmpty() || binding.fillContent.text.toString().isEmpty()
            ) {
                StyleableToast.makeText(requireContext(), "Please enter all information", R.style.ErrorToast).show()
            }
            else {
                AddNews()
            }
        }
    }

    private fun observerEvent() {
        registerAllExceptionEvent(saleViewModel, viewLifecycleOwner)
        registerObserverLoadingEvent(saleViewModel, viewLifecycleOwner)
        registerObserverNavigateEvent(saleViewModel, viewLifecycleOwner)
    }


    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAdminAddSalesBinding {
        return FragmentAdminAddSalesBinding.inflate(inflater, container, false)
    }

    private val onItemClick: (SaleJson) -> Unit = {
//        val action: NavDirections = SaleFragmentDirections.actionSaleFragmentToSaleDetailsFragment(it)
//        navigateAction(action)
    }

    private fun AddNews()
    {
        val salesRequest = SalesRequest(
            binding.fillTitle.text.toString(),
            binding.fillContent.text.toString(),
            binding.fillAuthor.text.toString(),
            binding.fillImg.text.toString(),
        )

        saleViewModel.createNews(salesRequest)
        saleViewModel.checkSuccess.observe(viewLifecycleOwner) {
            if (it == true) {
                StyleableToast.makeText(requireContext(), "Add Success", R.style.SuccessToast).show()
                navigateToPage(R.id.action_adminAddSalesFragment_to_adminSalesFragment)
            }
        }
    }

}
