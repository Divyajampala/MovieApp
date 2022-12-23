package com.movie.view

import android.text.TextUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.movie.R
import com.squareup.picasso.Picasso


const val BASE_URL = "https://image.tmdb.org/t/p/original/"

@BindingAdapter("imageUrl")
fun loadMovieImage(imageView: ImageView, url: String?) {
    if (!TextUtils.isEmpty(url)) {
        Picasso.get()
            .load(BASE_URL + url)
            .placeholder(R.drawable.ic_baseline_image_not_supported_24)
            .error(R.drawable.ic_baseline_image_not_supported_24)
            .into(imageView);
    }
}

@BindingAdapter("voteCount")
fun TextView.voteCount(count: String?) {
    if (!TextUtils.isEmpty(count)) {
        this.apply {
            count?.let {
                if (count.toInt() > 1000)
                    text = (count.toInt() / 1000).toString() + "K Votes"
                else
                    text = count + "Votes"
            }
        }
    }
}
