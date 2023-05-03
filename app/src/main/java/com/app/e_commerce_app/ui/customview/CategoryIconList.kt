package com.app.e_commerce_app.ui.customview

import android.app.Application
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.e_commerce_app.databinding.CustomCategoryListBinding
import com.app.e_commerce_app.ui.adapter.CategoryAdapter
import com.app.e_commerce_app.utils.OnCategoryIconButtonClick
import com.app.e_commerce_app.utils.Status
import com.app.e_commerce_app.viewmodel.CategoryViewModel

class CategoryIconList @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var _binding: CustomCategoryListBinding? = null

    private val binding get() = _binding!!

    private var categoryAdapter: CategoryAdapter? = null

    private val categoryViewModel: CategoryViewModel by lazy {
//        ViewModelProvider(findViewTreeViewModelStoreOwner()!!)[CategoryViewModel::class.java]
        ViewModelProvider(
            context as ViewModelStoreOwner,
            CategoryViewModel.CategoryViewModelFactory(context.applicationContext as Application)
        )[CategoryViewModel::class.java]
    }



    private val onCategoryIconClick : OnCategoryIconButtonClick = {

    }

    init {
        _binding =
            CustomCategoryListBinding.inflate(LayoutInflater.from(context), this, true)


        categoryAdapter = CategoryAdapter(context, onCategoryIconClick)
        binding.rvCategoryList.layoutManager =
            GridLayoutManager(this.context, 4)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _binding = null
    }

    fun setAdapter(
        onCategoryIconClick: OnCategoryIconButtonClick,
        adapter: CategoryAdapter? = null,
    ) {
        categoryAdapter = adapter ?: CategoryAdapter(context, onCategoryIconClick)
        binding.rvCategoryList.adapter = categoryAdapter
    }

    fun setLayoutManager(layoutManager: RecyclerView.LayoutManager) {
        binding.rvCategoryList.layoutManager = layoutManager
    }

    fun loadCategory() {
        if (categoryViewModel.categoriesData.value == null) {
            categoryViewModel.getAllCategories().observe(context as LifecycleOwner) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            resource.data?.let { data ->
                                categoryAdapter!!.setCategories(data.categories)
                            }
                        }
                        Status.ERROR -> {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                        }
                        Status.LOADING -> {
//                            Toast.makeText(context, "Category Loading", Toast.LENGTH_SHORT)
//                                .show()
                        }
                    }
                }
            }

        } else {
            val data = categoryViewModel.categoriesData.value!!
            categoryAdapter!!.setCategories(data)
        }
    }
}