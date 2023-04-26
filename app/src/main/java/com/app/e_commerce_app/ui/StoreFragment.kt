package com.app.e_commerce_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.app.e_commerce_app.R
import com.app.e_commerce_app.databinding.FragmentStoreBinding
import com.app.e_commerce_app.model.CategoryModel
import com.app.e_commerce_app.model.ProductModel
import com.app.e_commerce_app.ui.adapter.CategoryAdapter
import com.app.e_commerce_app.ui.adapter.ProductAdapter
import com.app.e_commerce_app.utils.Status
import com.app.e_commerce_app.viewmodel.CategoryViewModel
import com.app.e_commerce_app.viewmodel.ProductViewModel

class StoreFragment : Fragment(R.layout.fragment_store) {

    private var _binding: FragmentStoreBinding? = null
    private val binding get() = _binding!!
    private var categoryList: ArrayList<CategoryModel>? = null
    private var category_id : Int? = null
    private val categoryAdapter: CategoryAdapter by lazy {
        CategoryAdapter(requireContext(), onCategoryItemClick)
    }

    private val categoryViewModel: CategoryViewModel by lazy {
        ViewModelProvider(
            this,
            CategoryViewModel.CategoryViewModelFactory(requireActivity().application)
        )[CategoryViewModel::class.java]
    }

    private var productList: ArrayList<ProductModel>? = null

    private val productAdapter : ProductAdapter by lazy {
        ProductAdapter(requireContext(), onProductItemClick)
    }

    private val productViewModel: ProductViewModel by lazy {
        ViewModelProvider(
            this,
            ProductViewModel.ProductViewModelFactory(requireActivity().application)
        )[ProductViewModel::class.java]
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoreBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val controller = findNavController()
        //defalut category display
        category_id = 0;
//        binding.btnLogin.setOnClickListener {
//            controller.navigate(R.id.loginFragment)
//        }

//        binding.rvCategories.layoutManager = GridLayoutManager(this.context, 4)
//        binding.rvCategories.adapter = categoryAdapter
//        binding.rvProducts.layoutManager = GridLayoutManager(this.context, 2)
//        binding.rvProducts.adapter = productAdapter
        val bundle = arguments
        category_id = bundle?.getInt("category_id")
        loadCategory()
        loadProduct()

    }

    private fun loadCategory() {
        if (categoryList == null) {
            categoryList = ArrayList()

            categoryViewModel.getAllCategories().observe(viewLifecycleOwner) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            resource.data?.let { data ->
                                categoryList = data.categories
                                categoryAdapter.setCategories(categoryList as ArrayList<CategoryModel>)
                            }
                        }
                        Status.ERROR -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                        Status.LOADING -> {
//                            Toast.makeText(requireContext(), "Category Loading", Toast.LENGTH_SHORT)
//                                .show()
                        }
                    }
                }
            }
        }
    }
    private fun loadProductbyCategoryId(id : Int){

    }
    private fun loadProduct() {
        if(productList == null) {
            productList = ArrayList()
            productViewModel.getAllProducts().observe(viewLifecycleOwner) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                                resource.data?.let { data ->
                                    productList = data.products
                                    productAdapter.setProducts(productList as ArrayList<ProductModel>)
                                }
                        }
                        Status.ERROR -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                        }
                        Status.LOADING -> {
//                            Toast.makeText(requireContext(), "Product Loading", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

    }

    private val onCategoryItemClick: (CategoryModel) -> Unit = {
        Toast.makeText(requireContext(), it.categoryName, Toast.LENGTH_SHORT).show()
    }

    private val onProductItemClick: (ProductModel) -> Unit = {
        Toast.makeText(requireContext(), it.name, Toast.LENGTH_LONG).show()
    }
}