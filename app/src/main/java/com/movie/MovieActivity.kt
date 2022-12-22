package com.movie

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.movie.adapter.DashBoardViewPagerAdapter
import com.movie.databinding.ActivityMovieBinding
import com.movie.viewModel.MovieViewModel
import com.movie.viewModel.MovieViewModelFactory
import javax.inject.Inject

class MovieActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMovieBinding

    lateinit var movieViewModel: MovieViewModel

    @Inject
    lateinit var movieViewModelFactory: MovieViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie)
        (application as App).coreComponent.inject(this)
        mainBinding.dashBoardFragmentCardViewPager.setAdapter(
            DashBoardViewPagerAdapter(
                supportFragmentManager
            )
        )
        mainBinding.iconsTab.setupWithViewPager(mainBinding.dashBoardFragmentCardViewPager)
        mainBinding.iconsTab.setTabTextColors(
            Color.parseColor("#59b095"),
            Color.parseColor("#EF2020")
        );
    }
}
