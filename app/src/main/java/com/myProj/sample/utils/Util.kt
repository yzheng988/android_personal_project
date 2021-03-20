package com.myProj.sample.utils

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.myProj.sample.R

val PERMISSION_SEND_SMS = 123

fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply { 
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

/**
 * Extension function to load image with glide
 */
fun ImageView.loadimage(uri: String?, progressDrawable: CircularProgressDrawable) {
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_dog_icon)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)
}

// BindingAdapter makes the fcn available to the layout
@BindingAdapter("android:imageUrl")
fun loadImage(view: ImageView, url: String?) {
    view.loadimage(url, getProgressDrawable(view.context))
}