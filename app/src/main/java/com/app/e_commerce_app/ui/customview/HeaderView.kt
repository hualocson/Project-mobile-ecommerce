package com.app.e_commerce_app.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.app.e_commerce_app.R
import com.app.e_commerce_app.databinding.CustomHeaderViewBinding

class HeaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var _binding: CustomHeaderViewBinding? = null
    private val binding get() = _binding!!
    private var title: String? = "Title"
    private var isShowIcLeft: Boolean = true
    private var isShowIcRight: Boolean = true
    private var isShowTitle: Boolean = true
    private var icLeft: Int
    private var icRight: Int

    init {
        _binding = CustomHeaderViewBinding.inflate(LayoutInflater.from(context), this, true)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomHeader,
            defStyleAttr,
            0
        ).apply {
            try {
                // Retrieve the value of your custom attribute
                title = getString(R.styleable.CustomHeader_title)
                isShowIcLeft = getBoolean(R.styleable.CustomHeader_is_show_ic_left, true)
                isShowIcRight = getBoolean(R.styleable.CustomHeader_is_show_ic_right, true)
                isShowTitle = getBoolean(R.styleable.CustomHeader_is_show_title, true)
                icLeft = getResourceId(R.styleable.CustomHeader_ic_left, R.drawable.ic_back)
                icRight = getResourceId(R.styleable.CustomHeader_ic_right, R.drawable.ic_search_24)
            } finally {
                recycle()
            }
        }

        if (isShowTitle)
            binding.tvTittle.visibility = View.VISIBLE
        else
            binding.tvTittle.visibility = View.GONE

        binding.tvTittle.text = title

        if (isShowIcLeft) {
            binding.btnLeft.visibility = View.VISIBLE
            binding.btnLeft.icon = ContextCompat.getDrawable(context, icLeft)
        } else
            binding.btnLeft.visibility = View.GONE

        if (isShowIcRight) {
            binding.btnRight.visibility = View.VISIBLE
            binding.btnRight.icon = ContextCompat.getDrawable(context, icRight)
        } else
            binding.btnRight.visibility = View.GONE
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _binding = null
    }


    val btnLeft
        get(): Button {
            return binding.btnLeft
        }

    val btnRight
        get(): Button {
            return binding.btnRight
        }

    fun setTitle(title: String) {
        binding.tvTittle.text = title
    }
}