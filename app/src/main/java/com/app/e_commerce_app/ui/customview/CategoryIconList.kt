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
import androidx.lifecycle.findViewTreeLifecycleOwner
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

    val categoryViewModel: CategoryViewModel by lazy {
//        ViewModelProvider(context as ViewModelStoreOwner)[CategoryViewModel::class.java]
        ViewModelProvider(
            context as ViewModelStoreOwner,
            CategoryViewModel.CategoryViewModelFactory(context.applicationContext as Application)
        )[CategoryViewModel::class.java]
    }

    init {
        _binding =
            CustomCategoryListBinding.inflate(LayoutInflater.from(context), this, true)

        binding.lifecycleOwner  = findLifecycleOwner()
        binding.categoryIconViewModel = categoryViewModel

        binding.rvCategoryList.layoutManager =
            GridLayoutManager(this.context, 4)
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
        categoryViewModel.fetchAllCategories()
    }
}