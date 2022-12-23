package com.movie.view

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
import com.movie.model.MovieList
import com.movie.services.MovieCategory
import com.movie.viewModel.MovieViewModel
import com.movie.viewModel.MovieViewModelFactory
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class MovieListFragment() : Fragment() {

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
        val category = arguments?.getSerializable(CATEGORY) as MovieCategory
        movieViewModel.fetchMovieList(1, category)
        return mDataBinding?.getRoot()
    }

    private fun handleResponse(movie: MovieList) {
        mDataBinding.rvMovieList.adapter = movie?.results?.let { MovieAdapter(it) }
    }

    companion object {
        const val CATEGORY = "CATEGORY";
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
    }

    override fun onDestroy() {
        super.onDestroy()
        movieViewModel.clearObservables()
    }
}