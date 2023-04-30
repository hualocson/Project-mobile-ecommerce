package com.app.e_commerce_app.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.e_commerce_app.databinding.CustomCategoryListBinding
import com.app.e_commerce_app.model.CategoryModel
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


    private var categoryList: ArrayList<CategoryModel>? = null
    private var categoryAdapter: CategoryAdapter? = null
    private val categoryViewModel: CategoryViewModel by lazy {
        ViewModelProvider(findViewTreeViewModelStoreOwner()!!)[CategoryViewModel::class.java]
    }

    init {
        _binding =
            CustomCategoryListBinding.inflate(LayoutInflater.from(context), this, true)

        binding.rvCategoryList.layoutManager =
            GridLayoutManager(this.context, 4)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _binding = null

        categoryList!!.clear()
        categoryList = null
        categoryAdapter = null
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
        if (categoryList == null) {
            categoryList = ArrayList()

            categoryViewModel.getAllCategories().observe(findViewTreeLifecycleOwner()!!) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            resource.data?.let { data ->
                                categoryList = data.categories
                                categoryAdapter!!.setCategories(categoryList as ArrayList<CategoryModel>)
                            }
                        }
                        Status.ERROR -> {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
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
}