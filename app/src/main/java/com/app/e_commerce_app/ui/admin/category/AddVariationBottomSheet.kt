package com.app.e_commerce_app.ui.admin.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import com.app.e_commerce_app.databinding.FragmentAdminAddVariationSheetBinding
import com.app.e_commerce_app.ui.admin.category.viewmodel.AdminEditCategoryViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddVariationBottomSheet(private val data: String = "") : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentAdminAddVariationSheetBinding

    private val viewModel by activityViewModels<AdminEditCategoryViewModel>()

    companion object {
        const val ADD = "ADD new Variation"
        const val EDIT = "EDIT Variation"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (data.isNotBlank()) {
            val edt = binding.etVariationName.editText as EditText
            edt.setText(data)
        }
        binding.btnSave.setOnClickListener {
            saveAction()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdminAddVariationSheetBinding.inflate(inflater, container, false)
        return binding.root
    }


    private fun saveAction() {
        if (this.tag == ADD)
            viewModel.addToList(binding.etVariationName.editText?.text.toString())
        else {
            val curr = binding.etVariationName.editText?.text.toString()
            if (curr != data) {
                viewModel.updateData(data, curr)
            }
        }
        dismiss()
    }

}