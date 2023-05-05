package com.app.e_commerce_app.ui.customview

import android.app.Application
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.e_commerce_app.databinding.CustomCategoryRadioButtonListBinding
import com.app.e_commerce_app.ui.adapter.CategoryButtonAdapter
import com.app.e_commerce_app.utils.OnCategoryItemButtonClick
import com.app.e_commerce_app.viewmodel.CategoryViewModel

class CategoryRadioList @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var _binding: CustomCategoryRadioButtonListBinding? = null

    private val binding get() = _binding!!

    private var categoryButtonAdapter: CategoryButtonAdapter? = null
    private val categoryViewModel: CategoryViewModel by lazy {
        ViewModelProvider(
            context as ViewModelStoreOwner,
            CategoryViewModel.CategoryViewModelFactory(context.applicationContext as Application)
        )[CategoryViewModel::class.java]
    }

    init {
        _binding =
            CustomCategoryRadioButtonListBinding.inflate(LayoutInflater.from(context), this, true)

        binding.rvCategoryList.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        binding.lifecycleOwner = findLifecycleOwner()
        binding.categoryViewModel = categoryViewModel
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _binding = null
        categoryButtonAdapter = null
    }

    fun setAdapter(
        onCategoryItemButtonClick: OnCategoryItemButtonClick,
        adapter: CategoryButtonAdapter? = null,
    ) {
        categoryButtonAdapter = adapter ?: CategoryButtonAdapter(context, onCategoryItemButtonClick)
        binding.rvCategoryList.adapter = categoryButtonAdapter
    }

    fun setLayoutManager(layoutManager: RecyclerView.LayoutManager) {
        binding.rvCategoryList.layoutManager = layoutManager
    }

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

    fun loadCategory(id: Int = 0) {
        categoryViewModel.fetchAllCategories()
    }
//    fun loadCategory(id: Int = 0) {
//        if (categoryViewModel.categoriesData.value == null) {
//            categoryViewModel.getAllCategories().observe(context as LifecycleOwner) {
//                it?.let { resource ->
//                    when (resource.status) {
//                        Status.SUCCESS -> {
//                            resource.data?.let { data ->
//                                categoryButtonAdapter!!.setCategories(data.categories, id)
//                            }
//                        }
//                        Status.ERROR -> {
//                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
//                        }
//                        Status.LOADING -> {
//                            Toast.makeText(context, "Category Loading", Toast.LENGTH_SHORT)
//                                .show()
//                        }
//                    }
//                }
//            }
//
//        } else {
//            val data = categoryViewModel.categoriesData.value!!
//            categoryButtonAdapter!!.setCategories(data, id)
//        }

}

