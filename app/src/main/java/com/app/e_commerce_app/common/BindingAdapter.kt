package com.app.e_commerce_app.common

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.util.createCancellationSignal
import com.app.e_commerce_app.databinding.ItemAddressBinding
import com.app.e_commerce_app.databinding.ItemShippingMethodBinding
import com.app.e_commerce_app.model.AddressJson
import com.app.e_commerce_app.model.ShippingJson
import com.app.e_commerce_app.model.variation.VariationOptionModel
import com.app.e_commerce_app.ui.adapter.VariationOptionAdapter
import com.app.e_commerce_app.utils.OnVariationOptionClick
import com.app.e_commerce_app.utils.Utils
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

@BindingAdapter( "imageId")
fun loadImageResource(view: ImageView, id: Int? = null) {
    if(id != null)
        view.setImageResource(id)
}

@BindingAdapter("imageSlideList")
fun setImageList(view: ImageSlider, list: ArrayList<SlideModel>?) {
    if (!list.isNullOrEmpty())
        view.setImageList(list, ScaleTypes.CENTER_INSIDE)
}

@BindingAdapter("items")
fun <T> setItems(recyclerView: RecyclerView, items: List<T>?) {
    if (items.isNullOrEmpty()) return
    Log.d("LOAD", items.toString())
    val adapter = recyclerView.adapter as? BindableAdapter<T>
    adapter?.setItems(items)
}

interface BindableAdapter<T> {
    fun setItems(items: List<T>)
}

@BindingAdapter("address")
fun setAddressData(view: View, addressJson: AddressJson?) {
    if(addressJson == null)
        return
    val binding = DataBindingUtil.bind<ItemAddressBinding>(view)
    binding?.let {
        it.addressData = addressJson
        it.executePendingBindings()
    }
}

@BindingAdapter("shippingMethod")
fun setShippingMethod(view: View, shippingJson: ShippingJson?) {
    if(shippingJson == null)
        return
    val binding = DataBindingUtil.bind<ItemShippingMethodBinding>(view)
    binding?.let {
        it.shippingMethod = shippingJson
        it.executePendingBindings()
    }
}

@BindingAdapter("numberValue")
fun setNumberValue(textView: TextView, value: Long?) {
    var number = value
    if(number == null)
        number = 0
    number.let {
//        val formattedValue = Utils.formatNumber(number)
        textView.text = number.toString()
    }
}


@BindingAdapter("numberValue")
fun setNumberValue(textView: TextView, value: Int?) {
    var number = value
    if(number == null)
        number = 0
    number.let {
//        val formattedValue = Utils.formatNumber(number)
        textView.text = number.toString()
    }
}