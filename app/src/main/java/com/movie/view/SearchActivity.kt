package com.movie.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.movie.R
import com.movie.databinding.ActivityMovieBinding
import com.movie.databinding.ActivitySearchBinding


class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivitySearchBinding>(this, R.layout.activity_search)
    }
}
