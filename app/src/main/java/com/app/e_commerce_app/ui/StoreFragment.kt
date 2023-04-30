package com.app.e_commerce_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.e_commerce_app.R
import com.app.e_commerce_app.databinding.FragmentStoreBinding
import com.app.e_commerce_app.model.CategoryModel
import com.app.e_commerce_app.model.CategoryRadioButton
import com.app.e_commerce_app.ui.adapter.CategoryButtonAdapter
import com.app.e_commerce_app.utils.OnCategoryItemButtonClick
import com.app.e_commerce_app.utils.OnProductItemClick
import com.app.e_commerce_app.utils.Status
import com.app.e_commerce_app.viewmodel.CategoryViewModel


class StoreFragment : Fragment(R.layout.fragment_store) {

    private var _binding: FragmentStoreBinding? = null
    private val binding get() = _binding!!
    private var categoryId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoreBinding.inflate(inflater, container, false)
        binding.layoutProductList.setAdapter(onProductItemClick)
        binding.layoutCategoryList.setAdapter(onCategoryItemButtonClick)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val controller = findNavController()


        val bundle = arguments
        categoryId = bundle!!.getInt("category_id")

        if(categoryId == 0) {
            binding.storeHeader.setTitle("Most Popular")
        }
        binding.layoutProductList.loadProductByCategoryId(categoryId)
        binding.layoutCategoryList.loadCategory(categoryId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private val onCategoryItemButtonClick: OnCategoryItemButtonClick = { radioButton ->
        binding.layoutProductList.loadProductByCategoryId(radioButton.id)
    }

    private val onProductItemClick: OnProductItemClick = {
        Toast.makeText(context, it.name, Toast.LENGTH_LONG).show()
    }
}