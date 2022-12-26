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

    private var movieId: Int = -1
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
        movieId = intent.getIntExtra(MOVIE_ID, 0)
        movieViewModel.getMovieDetails(movieId)

    }

    private fun initView() {
        mDataBinding.favIcon.apply {
            setOnClickListener {
                if (drawable.constantState == resources.getDrawable(
                        R.drawable.ic_baseline_thumb_up_24,
                        null
                    ).constantState
                ) {
                    movieViewModel.unsetFavMovieState(movieId)
                    unsetFavIcon()
                } else {
                    movieViewModel.setFavMovieState(movieId)
                    setFavIcon()
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

        movieViewModel.favMovieState.observe(this, Observer { response ->
            if (response) {
                setFavIcon()
            } else {
                unsetFavIcon()
            }
        })

        movieViewModel.loadingState.observe(this, Observer { response ->
            when (response) {
                NetworkState.SUCCESS -> {
                    hideProgressView()
                    movieViewModel.checkFavMovieState(movieId)
                    mDataBinding.tvMessage.visibility = View.GONE
                    mDataBinding.detailsContentLayout.visibility = View.VISIBLE
                }
                NetworkState.ERROR -> {
                    hideProgressView()
                    mDataBinding.tvMessage.visibility = View.VISIBLE
                    mDataBinding.detailsContentLayout.visibility = View.GONE
                }
                NetworkState.LOADING -> {
                    mDataBinding.tvMessage.visibility = View.GONE
                    mDataBinding.detailsContentLayout.visibility = View.GONE
                    showProgressView()
                }
            }
        })
    }

    private fun setFavIcon() {
        mDataBinding.favIcon.setImageResource(R.drawable.ic_baseline_thumb_up_24)
    }

    private fun unsetFavIcon() {
        mDataBinding.favIcon.setImageResource(R.drawable.ic_baseline_thumb_up_off_alt_24)
    }

    private fun handleResponse(response: Movie) {
        mDataBinding.movie = response
        mDataBinding.rvGenreList.adapter = response.genres?.mapNotNull { it.name }
            ?.let { list -> GenreAdapter(list) }
    }

    fun hideProgressView() {
        mDataBinding.progressLayout.visibility = View.GONE
    }

    fun showProgressView() {
        mDataBinding.progressLayout.visibility = View.VISIBLE
    }
}
