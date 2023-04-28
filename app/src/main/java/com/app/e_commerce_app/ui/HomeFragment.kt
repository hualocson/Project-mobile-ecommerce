package com.app.e_commerce_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.e_commerce_app.R
import com.app.e_commerce_app.databinding.FragmentHomepageBinding
import com.app.e_commerce_app.model.CategoryModel
import com.app.e_commerce_app.model.CategoryRadioButton
import com.app.e_commerce_app.model.ProductModel
import com.app.e_commerce_app.ui.adapter.CategoryAdapter
import com.app.e_commerce_app.ui.adapter.CategoryButtonAdapter
import com.app.e_commerce_app.ui.adapter.ProductAdapter
import com.app.e_commerce_app.utils.Status
import com.app.e_commerce_app.viewmodel.CategoryViewModel
import com.app.e_commerce_app.viewmodel.ProductViewModel
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel

class HomeFragment : Fragment(R.layout.fragment_homepage) {

    private var _binding: FragmentHomepageBinding? = null
    private val binding get() = _binding!!
    private var imageList: ArrayList<SlideModel>? = null
    private var categoryList: ArrayList<CategoryModel>? = null
    private val categoryAdapter: CategoryAdapter by lazy {
        CategoryAdapter(requireContext(), onCategoryItemClick)
    }
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
        _binding = FragmentHomepageBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        imageList!!.clear()
        imageList = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadSlider()

        val controller = findNavController()
//        binding.btnLogin.setOnClickListener {
//            controller.navigate(R.id.loginFragment)
//        }


        binding.rvCategoriesButton.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvCategoriesButton.adapter = categoryButtonAdapter

        binding.rvCategories.layoutManager = GridLayoutManager(this.context, 4)
        binding.rvCategories.adapter = categoryAdapter

        binding.rvProducts.layoutManager = GridLayoutManager(this.context, 2)
        binding.rvProducts.adapter = productAdapter

        loadCategory()
        loadProduct()


        binding.imageProfile.setOnClickListener {
            controller.navigate(R.id.fillProfileFragment)
        }
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
                                categoryButtonAdapter.setCategories(categoryList as ArrayList<CategoryModel>)
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
                        binding.pgbProducts.visibility = View.GONE
                        binding.rvProducts.alpha = 1.0f
                    }
                    Status.ERROR -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                        binding.pgbProducts.visibility = View.GONE
                        binding.rvProducts.alpha = 1.0f
                    }
                    Status.LOADING -> {
                        binding.pgbProducts.visibility = View.VISIBLE
                        binding.pgbProducts.bringToFront()
                        productList!!.clear()
                        binding.rvProducts.alpha = 0.5f
                    }
                }
            }
        }
    }

    private fun loadProductByCategoryId(id: Int) {
        if (productList == null) {
            productList = ArrayList()
        }
        productViewModel.getProductsByCategory(id).observe(viewLifecycleOwner) {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { data ->
                            productList = data.products
                            productAdapter.setProducts(productList as ArrayList<ProductModel>)
                        }
                        binding.pgbProducts.visibility = View.GONE
                        binding.rvProducts.alpha = 1.0f
                    }
                    Status.ERROR -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        binding.pgbProducts.visibility = View.GONE
                        binding.rvProducts.alpha = 1.0f
                    }
                    Status.LOADING -> {
                        binding.pgbProducts.visibility = View.VISIBLE
                        productList!!.clear()
                        binding.pgbProducts.bringToFront()
                        binding.rvProducts.alpha = 0.5f
                    }
                }
            }
        }
    }

    private val onCategoryItemClick: (CategoryModel) -> Unit = {
        val controller = findNavController()
        val bundle = bundleOf(
            "category_id" to it.id
        )
        controller.navigate(R.id.storeFragment, bundle)
    }

    private val onCategoryItemButtonClick: (CategoryRadioButton) -> Unit = {
        if (it.id == 0)
            loadProduct()
        else loadProductByCategoryId(it.id)
    }

    private val onProductItemClick: (ProductModel) -> Unit = {
        Toast.makeText(requireContext(), it.name, Toast.LENGTH_LONG).show()
    }

    private fun loadSlider() {
        if (imageList == null) {
            imageList = ArrayList()
            imageList!!.add(SlideModel(R.drawable.image1));
            imageList!!.add(SlideModel(R.drawable.image2));
            imageList!!.add(SlideModel(R.drawable.image3));
            imageList!!.add(SlideModel(R.drawable.image4));
            binding.imageSlider.setImageList(imageList!!, ScaleTypes.FIT)
        }
    }
}