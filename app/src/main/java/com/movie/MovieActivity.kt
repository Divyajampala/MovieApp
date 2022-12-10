package com.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.movie.adapter.GenreAdapter
import com.movie.databinding.ActivityMovieBinding
import com.movie.model.Movie
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
        initViewModel()
        initObservers()
        movieViewModel.fetchMovieList()
    }

    private fun initViewModel() {
        movieViewModel = ViewModelProviders.of(this, movieViewModelFactory)
            .get(MovieViewModel::class.java)
    }

    private fun initObservers() {
        movieViewModel.movieListResponse.observe(this, Observer { response ->
            handleResponse(response)
        })
    }

    private fun handleResponse(movie: Movie?) {
        mainBinding.rvGenreList.adapter = movie?.genres?.let { GenreAdapter(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        movieViewModel.clearObservables()
    }
}
