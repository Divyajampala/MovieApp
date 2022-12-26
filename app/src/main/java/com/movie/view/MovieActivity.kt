package com.movie.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.movie.R
import com.movie.adapter.DashBoardViewPagerAdapter
import com.movie.databinding.ActivityMovieBinding

class MovieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMovieBinding>(this, R.layout.activity_movie).apply {
            iconsTab.setupWithViewPager(dashBoardFragmentCardViewPager)
            dashBoardFragmentCardViewPager.setAdapter(
                DashBoardViewPagerAdapter(
                    supportFragmentManager
                )
            )
            searchView.setOnClickListener {
                startActivity(Intent(baseContext, SearchActivity::class.java))
            }
        }
    }
}
