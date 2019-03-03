package com.example.githubjobs.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("cover")
fun cover(view: ImageView, URL: String?) {
    Picasso.get()
        .load(URL)
        .into(view)
}