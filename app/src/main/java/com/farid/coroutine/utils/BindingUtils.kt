package com.farid.coroutine.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.farid.coroutine.ui.movies_list.MoviesListAdapter


@BindingAdapter("setText")
fun setText(textView: TextView, text: String) {
    textView.text = text
}

@BindingAdapter("setAdapter")
fun setAdapter(recyclerView: RecyclerView, recyclerAdapter: MoviesListAdapter) {
    recyclerView.adapter = recyclerAdapter
}

@BindingAdapter("loadImage")
fun loadImage(imageView: ImageView, url:String) {
    imageView.load(url)
}