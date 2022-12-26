package com.movie.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.movie.App
import com.movie.R
import com.movie.adapter.GenreAdapter
import com.movie.databinding.ActivityDetailedMovieBinding
import com.movie.model.Movie
import com.movie.view.MovieListFragment.Companion.MOVIE_ID
import com.movie.viewModel.MovieViewModel
import com.movie.viewModel.MovieViewModelFactory
import javax.inject.Inject

class MovieDetailedActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityDetailedMovieBinding

    lateinit var movieViewModel: MovieViewModel

    @Inject
    lateinit var movieViewModelFactory: MovieViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_detailed_movie)
        (application as App).coreComponent.inject(this)
        initViewModel()
        initObservers()
        movieViewModel.getMovieDetails(intent.getIntExtra(MOVIE_ID, 0))

    }

    private fun initViewModel() {
        movieViewModel = ViewModelProviders.of(this, movieViewModelFactory)
            .get(MovieViewModel::class.java)
    }

    private fun initObservers() {
        movieViewModel.movieDetailsResponse.observe(this, Observer { response ->
            handleResponse(response)
        })
    }

    private fun handleResponse(response: Movie) {
        mainBinding.movie = response
        mainBinding.rvGenreList.adapter = response.genres?.mapNotNull { it.name }
            ?.let { list -> GenreAdapter(list) }
    }
}
