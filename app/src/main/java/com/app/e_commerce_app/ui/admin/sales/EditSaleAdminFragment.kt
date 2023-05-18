package com.app.e_commerce_app.ui.admin.sales

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.app.e_commerce_app.R
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentAdminSalesEditBinding
import com.app.e_commerce_app.databinding.FragmentNewsFullBinding
import com.app.e_commerce_app.model.SalesRequest
import com.app.e_commerce_app.viewmodel.SaleViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.github.muddz.styleabletoast.StyleableToast

@AndroidEntryPoint
class EditSaleAdminFragment : BaseFragment<FragmentAdminSalesEditBinding>(true) {

    private val args by navArgs<EditSaleAdminFragmentArgs>()

    private val saleViewModel by viewModels<SaleViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        val id: Int
        observerEvent()
        if(args.saleModel != null){
            binding.saleModel = args.saleModel
        }
        binding.headerView.btnLeft.setOnClickListener {
            navigateBack()
        }
        binding.btnUpdate.setOnClickListener {
            //Check null for all fields
            if (binding.edtTitle.text.toString().isEmpty() || binding.edtAuthor.text.toString()
                    .isEmpty() || binding.edtContent.text.toString().isEmpty() || binding.edtImgurl.text.toString().isEmpty()
            ) {
                StyleableToast.makeText(requireContext(), "Please enter all information", R.style.ErrorToast).show()
            }
            else {
                UpdateNews(args.saleModel!!.id)
            }
        }
        binding.btnDelete.setOnClickListener {
            DeleteNews(args.saleModel!!.id)
        }
    }
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAdminSalesEditBinding {
        return FragmentAdminSalesEditBinding.inflate(inflater, container, false)
    }

    private fun observerEvent() {
        registerAllExceptionEvent(saleViewModel, viewLifecycleOwner)
        registerObserverLoadingEvent(saleViewModel, viewLifecycleOwner)
        registerObserverNavigateEvent(saleViewModel, viewLifecycleOwner)
    }

    private fun UpdateNews(id: Int)
    {
        val salesRequest = SalesRequest(
            binding.edtTitle.text.toString(),
            binding.edtContent.text.toString(),
            binding.edtAuthor.text.toString(),
            binding.edtImgurl.text.toString(),
        )

        saleViewModel.updateNews(id, salesRequest)
        saleViewModel.checkSuccess.observe(viewLifecycleOwner) {
            if (it == true) {
                StyleableToast.makeText(requireContext(), "Update Success", R.style.SuccessToast).show()
            }
        }
    }

    private fun DeleteNews(id: Int)
    {
        saleViewModel.deleteNews(id)
        saleViewModel.checkSuccess.observe(viewLifecycleOwner) {
            if (it == true) {
                StyleableToast.makeText(requireContext(), "Delete Success", R.style.SuccessToast).show()
                navigateToPage(R.id.action_adminEditSalesFragment_to_adminSalesFragment)
            }
        }
    }
}
