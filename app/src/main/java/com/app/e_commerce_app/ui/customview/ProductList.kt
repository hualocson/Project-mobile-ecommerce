package com.app.e_commerce_app.ui.customview

import android.app.Application
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.e_commerce_app.databinding.CustomProductListBinding
import com.app.e_commerce_app.ui.adapter.ProductAdapter
import com.app.e_commerce_app.utils.OnProductItemClick
import com.app.e_commerce_app.viewmodel.ProductViewModel

class ProductList @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var _binding: CustomProductListBinding? = null
    private val binding get() = _binding!!

    private var productAdapter: ProductAdapter? = null

    private val productViewModel: ProductViewModel by lazy {
        ViewModelProvider(
            context as ViewModelStoreOwner,
            ProductViewModel.ProductViewModelFactory(context.applicationContext as Application)
        )[ProductViewModel::class.java]
    }

    init {
        _binding = CustomProductListBinding.inflate(LayoutInflater.from(context), this, true)
        binding.lifecycleOwner  = findLifecycleOwner()
        productViewModel.fetchAllProducts()
        binding.rvProductList.layoutManager = GridLayoutManager(context, 2)
    }

//    private fun loadProduct() {
//        if(productViewModel.productsData.value != null) {
//            val data = productViewModel.productsData.value
//            productAdapter!!.setProducts(data as ArrayList<ProductModel>)
//        }
//        else {
//            productViewModel.getAllProducts().observe(context as LifecycleOwner) {
//                it?.let { resource ->
//                    when (resource.status) {
//                        Status.SUCCESS -> {
//                            resource.data?.let { data ->
//                                productAdapter!!.setProducts(data.products)
//                            }
//                            binding.rvProductList.visibility = View.VISIBLE
//                        }
//                        Status.ERROR -> {
//                            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
//                            binding.rvProductList.visibility = View.VISIBLE
//                        }
//                        Status.LOADING -> {
//                            binding.rvProductList.visibility = View.INVISIBLE
//                        }
//                    }
//                }
//            }
//        }
//
//    }


    private fun findLifecycleOwner(): LifecycleOwner {
        var parent = parent
        while (parent != null) {
            if (parent is LifecycleOwner) {
                return parent
            }
            parent = parent.parent
        }
        return context as LifecycleOwner
    }


    private fun loadProduct() {
        productViewModel.fetchAllProducts()
    }

    fun loadProductByCategoryId(id: Int) {
        if (id == 0)
            loadProduct()
        else
            productViewModel.fetchProductByCategoryId(id)
    }

//    fun loadProductByCategoryId(id: Int) {
//        if (productList == null) {
//            productList = ArrayList()
//        }
//        if (id != 0) {
//            productViewModel.getProductsByCategory(id).observe(context as LifecycleOwner) {
//                it?.let { resource ->
//                    when (resource.status) {
//                        Status.SUCCESS -> {
//                            resource.data?.let { data ->
//                                productList = data.products
//                                productAdapter!!.setProducts(productList as ArrayList<ProductModel>)
//                            }
//                            binding.rvProductList.visibility = View.VISIBLE
//                        }
//                        Status.ERROR -> {
//                            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
//                            binding.rvProductList.visibility = View.VISIBLE
//                        }
//                        Status.LOADING -> {
//                            productList!!.clear()
//                            binding.rvProductList.visibility = View.INVISIBLE
//                        }
//                    }
//                }
//            }
//        } else
//            loadProduct()
//    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _binding = null
        productAdapter = null
    }

    fun setAdapter(onProductItemClick: OnProductItemClick, adapter: ProductAdapter? = null) {
        productAdapter = adapter ?: ProductAdapter(context, onProductItemClick)
        binding.rvProductList.adapter = productAdapter
    }

    fun setLayoutManager(layoutManager: RecyclerView.LayoutManager) {
        binding.rvProductList.layoutManager = layoutManager
    }
}