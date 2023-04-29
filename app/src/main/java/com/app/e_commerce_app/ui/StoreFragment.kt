package com.app.e_commerce_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.e_commerce_app.R
import com.app.e_commerce_app.databinding.FragmentStoreBinding
import com.app.e_commerce_app.model.CategoryModel
import com.app.e_commerce_app.model.CategoryRadioButton
import com.app.e_commerce_app.model.ProductModel
import com.app.e_commerce_app.ui.adapter.CategoryButtonAdapter
import com.app.e_commerce_app.ui.adapter.ProductAdapter
import com.app.e_commerce_app.utils.Status
import com.app.e_commerce_app.viewmodel.CategoryViewModel
import com.app.e_commerce_app.viewmodel.ProductViewModel


class StoreFragment : Fragment(R.layout.fragment_store) {

    private var _binding: FragmentStoreBinding? = null
    private val binding get() = _binding!!
    private var categoryList: ArrayList<CategoryModel>? = null
    private var categoryId: Int = 0

    private val categoryButtonAdapter: CategoryButtonAdapter by lazy {
        CategoryButtonAdapter(requireContext(), onCategoryItemButtonClick)
    }


    private val categoryViewModel: CategoryViewModel by lazy {
        ViewModelProvider(
            this,
            CategoryViewModel.CategoryViewModelFactory(requireActivity().application)
        )[CategoryViewModel::class.java]
    }

    private var productList: ArrayList<ProductModel>? = null

    private val productAdapter: ProductAdapter by lazy {
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val controller = findNavController()

        binding.navigateStore.btnNavigateBack.setOnClickListener {
            controller.navigate(R.id.action_storeFragment_to_homeFragment)
        }

        binding.rvCategoriesStore.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvCategoriesStore.adapter = categoryButtonAdapter


        binding.productListView.rvProductList.layoutManager = GridLayoutManager(this.context, 2)
        binding.productListView.rvProductList.adapter = productAdapter

        val bundle = arguments
        categoryId = bundle!!.getInt("category_id")


        loadCategory(categoryId)
        loadProductByCategoryId(categoryId)
    }

    private fun loadCategory(categoryId: Int) {
        if (categoryList == null) {
            categoryList = ArrayList()

            categoryViewModel.getAllCategories().observe(viewLifecycleOwner) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            resource.data?.let { data ->
                                categoryList = data.categories
                                categoryButtonAdapter.setCategories(categoryList as ArrayList<CategoryModel>, categoryId)
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

    private fun loadProductByCategoryId(id: Int) {
        if (productList == null) {
            productList = ArrayList()
        }
        if (id != 0) {
            productViewModel.getProductsByCategory(id).observe(viewLifecycleOwner) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            resource.data?.let { data ->
                                productList = data.products
                                productAdapter.setProducts(productList as ArrayList<ProductModel>)
                            }
                            binding.productListView.pgbProducts.visibility = View.GONE
                            binding.productListView.rvProductList.alpha = 1.0f
                        }
                        Status.ERROR -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                            binding.productListView.pgbProducts.visibility = View.GONE
                            binding.productListView.rvProductList.alpha = 1.0f
                        }
                        Status.LOADING -> {
                            Toast.makeText(requireContext(), "Product Loading", Toast.LENGTH_SHORT)
                                .show()
                            binding.productListView.pgbProducts.visibility = View.VISIBLE
                            binding.productListView.pgbProducts.bringToFront()
                            productList!!.clear()
                            binding.productListView.rvProductList.alpha = 0.5f
                        }
                    }
                }
            }
        } else
            loadProduct()
    }

    private val onProductItemClick: (ProductModel) -> Unit = {
        Toast.makeText(requireContext(), it.name, Toast.LENGTH_LONG).show()
    }

    private fun loadProduct() {
        if (productList == null)
            productList = ArrayList()

        productViewModel.getAllProducts().observe(viewLifecycleOwner) {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { data ->
                            productList = data.products
                            productAdapter.setProducts(productList as ArrayList<ProductModel>)
                        }
                        binding.productListView.pgbProducts.visibility = View.GONE
                        binding.productListView.rvProductList.alpha = 1f
                    }
                    Status.ERROR -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                        binding.productListView.pgbProducts.visibility = View.GONE
                        binding.productListView.rvProductList.alpha = 1f
                    }
                    Status.LOADING -> {
                        binding.productListView.pgbProducts.visibility = View.VISIBLE
                        binding.productListView.pgbProducts.bringToFront()
                        productList!!.clear()
                        binding.productListView.rvProductList.alpha = 0.5f
                    }
                }
            }
        }
    }

    private val onCategoryItemButtonClick: (CategoryRadioButton) -> Unit = {
        if (it.id == 0)
            loadProduct()
        else loadProductByCategoryId(it.id)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        productList!!.clear()
        productList = null

        categoryList!!.clear()
        categoryList = null
    }

}