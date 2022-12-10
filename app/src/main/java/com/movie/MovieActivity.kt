package com.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.movie.databinding.ActivityMovieBinding

class MovieActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
//        mainBinding.rvMovieList.adapter
    }
}
