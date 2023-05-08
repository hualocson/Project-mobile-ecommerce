package com.app.e_commerce_app.common

import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.util.createCancellationSignal
import com.app.e_commerce_app.model.variation.VariationOptionModel
import com.app.e_commerce_app.ui.adapter.VariationOptionAdapter
import com.app.e_commerce_app.utils.OnVariationOptionClick
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.squareup.picasso.Picasso

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        Picasso.get().load(url).into(view)
    }
}

@BindingAdapter("imageSlideList")
fun setImageList(view: ImageSlider, list: ArrayList<SlideModel>?) {
    if (!list.isNullOrEmpty())
        view.setImageList(list, ScaleTypes.FIT)
}

@BindingAdapter("items")
fun <T> setItems(recyclerView: RecyclerView, items: List<T>?) {
    if (items.isNullOrEmpty()) return
    val adapter = recyclerView.adapter as? BindableAdapter<T>
    adapter?.setItems(items)
}

interface BindableAdapter<T> {
    fun setItems(items: List<T>)
}