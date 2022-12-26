package com.movie.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.movie.App
import com.movie.R
import com.movie.adapter.MovieAdapter
import com.movie.databinding.FragmentMovieListBinding
import com.movie.model.Genre
import com.movie.model.MovieList
import com.movie.services.MovieCategory
import com.movie.viewModel.MovieViewModel
import com.movie.viewModel.MovieViewModelFactory
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class MovieListFragment() : Fragment() {

    lateinit private var genreList: List<Genre>
    private lateinit var mDataBinding: FragmentMovieListBinding
    lateinit var movieViewModel: MovieViewModel

    @Inject
    lateinit var movieViewModelFactory: MovieViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as App).coreComponent.inject(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mDataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_movie_list, container, false
        )
        initViewModel()
        initObservers()
        movieViewModel.getGenreList()
        return mDataBinding?.getRoot()
    }

    private fun handleResponse(movie: MovieList) {
        mDataBinding.rvMovieList.adapter = movie?.results?.let {
            MovieAdapter(it,genreList) { id ->
                var intent = Intent(activity, MovieDetailedActivity::class.java)
                intent.putExtra(MOVIE_ID, id)
                startActivity(intent)
            }
        }
    }

    companion object {
        private const val CATEGORY = "CATEGORY";
        const val MOVIE_ID = "MOVIE_ID";
        fun getInstance(category: MovieCategory): MovieListFragment {
            val fragment = MovieListFragment()
            val args = Bundle()
            args.putSerializable(CATEGORY, category)
            fragment.setArguments(args)
            return fragment
        }
    }

    private fun initViewModel() {
        movieViewModel = ViewModelProviders.of(this, movieViewModelFactory)
            .get(MovieViewModel::class.java)
    }

    private fun initObservers() {
        activity?.let {
            movieViewModel.movieListResponse.observe(it, Observer { response ->
                handleResponse(response)
            })
        }

        activity?.let {
            movieViewModel.genreResponse.observe(it, Observer { response ->
                val category = arguments?.getSerializable(CATEGORY) as MovieCategory
                genreList = response
                movieViewModel.fetchMovieList(1, category)
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        movieViewModel.clearObservables()
    }
}