package com.movie.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.movie.App
import com.movie.R
import com.movie.adapter.GenreAdapter
import com.movie.databinding.ActivityDetailedMovieBinding
import com.movie.model.Movie
import com.movie.model.NetworkState
import com.movie.view.MovieListFragment.Companion.MOVIE_ID
import com.movie.viewModel.MovieViewModel
import com.movie.viewModel.MovieViewModelFactory
import javax.inject.Inject

class MovieDetailedActivity : AppCompatActivity() {

    private lateinit var mDataBinding: ActivityDetailedMovieBinding

    lateinit var movieViewModel: MovieViewModel

    @Inject
    lateinit var movieViewModelFactory: MovieViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_detailed_movie)
        (application as App).coreComponent.inject(this)
        initView()
        initViewModel()
        initObservers()
        movieViewModel.getMovieDetails(intent.getIntExtra(MOVIE_ID, 0))

    }

    private fun initView() {
        mDataBinding.favIcon.apply {
            setOnClickListener {
                if (drawable.constantState == resources.getDrawable(R.drawable.ic_baseline_thumb_up_24).constantState) {
                    setImageResource(R.drawable.ic_baseline_thumb_up_off_alt_24)
                } else {
                    setImageResource(R.drawable.ic_baseline_thumb_up_24)
                }
            }
        }
    }

    private fun initViewModel() {
        movieViewModel = ViewModelProviders.of(this, movieViewModelFactory)
            .get(MovieViewModel::class.java)
    }

    private fun initObservers() {
        movieViewModel.movieDetailsResponse.observe(this, Observer { response ->
            handleResponse(response)
        })

        movieViewModel.loadingState.observe(this, Observer { response ->
            when (response) {
                NetworkState.SUCCESS -> {
                    hideProgressBar()
                    mDataBinding.tvMessage.visibility = View.GONE
                    mDataBinding.detailsContentLayout.visibility = View.VISIBLE
                }
                NetworkState.ERROR -> {
                    hideProgressBar()
                    mDataBinding.tvMessage.visibility = View.VISIBLE
                    mDataBinding.detailsContentLayout.visibility = View.GONE
                }
                NetworkState.LOADING -> {
                    mDataBinding.tvMessage.visibility = View.GONE
                    mDataBinding.detailsContentLayout.visibility = View.GONE
                    showProgressBar()
                }
            }
        })
    }

    private fun handleResponse(response: Movie) {
        mDataBinding.movie = response
        mDataBinding.rvGenreList.adapter = response.genres?.mapNotNull { it.name }
            ?.let { list -> GenreAdapter(list) }
    }

    fun hideProgressBar() {
        mDataBinding.progressLayout.visibility = View.GONE
    }

    fun showProgressBar() {
        mDataBinding.progressLayout.visibility = View.VISIBLE
    }


}
