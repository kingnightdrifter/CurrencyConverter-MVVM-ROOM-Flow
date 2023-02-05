package com.street.paypay_currencyconverter.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.street.paypay_currencyconverter.R


@BindingAdapter("loadImage")
fun loadLoadingImage(imageView: ImageView, useless: Int){
    Glide.with(imageView.context)
        .load(R.drawable.loader_icon)
        .into(imageView)
}