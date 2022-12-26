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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.movie.App
import com.movie.R
import com.movie.adapter.MovieAdapter
import com.movie.databinding.FragmentMovieListBinding
import com.movie.model.Genre
import com.movie.model.MovieList
import com.movie.model.NetworkState
import com.movie.model.Result
import com.movie.services.MovieCategory
import com.movie.viewModel.MovieViewModel
import com.movie.viewModel.MovieViewModelFactory
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
open class MovieListFragment() : Fragment() {

    lateinit private var genreList: List<Genre>

    private var category: MovieCategory? = null

    protected lateinit var mDataBinding: FragmentMovieListBinding

    lateinit var movieViewModel: MovieViewModel

    //Pagination
    protected var isLastPage = false

    protected var currentPage = 0

    protected var isLoading = false

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
        initView()
        initViewModel()
        initObservers()
        category = arguments?.getSerializable(CATEGORY) as MovieCategory?
        movieViewModel.getGenreList()
        return mDataBinding?.getRoot()
    }

    open fun initView() {
        mDataBinding.rvMovieList.adapter =
            MovieAdapter(onClick = { id ->
                var intent = Intent(activity, MovieDetailedActivity::class.java)
                intent.putExtra(MOVIE_ID, id)
                startActivity(intent)
            })
        mDataBinding?.rvMovieList?.addOnScrollListener(recyclerViewOnScrollListener)
    }

    private fun handleResponse(movie: List<Result>) {
        (mDataBinding.rvMovieList.adapter as MovieAdapter).apply {
            this.movieList.addAll(movie)
            if (movieList.size == 0) {
                mDataBinding.tvMessage.visibility = View.VISIBLE
            }
            notifyDataSetChanged()
        }
    }

    companion object {

        private const val CATEGORY = "CATEGORY"

        const val MOVIE_ID = "MOVIE_ID"

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
        movieViewModel.movieListResponse.observe(viewLifecycleOwner, Observer { response ->
            setResponseData(response)
        })

        movieViewModel.genreResponse.observe(viewLifecycleOwner, Observer { response ->
            if (response.isNullOrEmpty().not()) {
                genreList = response
                // Fetch Movie List
                fetchMovieList()
            }
        })

        movieViewModel.loadingState.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                NetworkState.SUCCESS -> {
                    hideProgressView()
                }
                NetworkState.ERROR -> {
                    hideProgressView()
                    mDataBinding.tvMessage.visibility = View.VISIBLE
                }
                NetworkState.LOADING -> {
                    mDataBinding.tvMessage.visibility = View.GONE
                    showProgressView()
                }
            }
        })
    }

    open fun fetchMovieList() {
        category?.let { category -> movieViewModel.fetchMovieList(++currentPage, category) }
    }

    override fun onDestroy() {
        super.onDestroy()
        movieViewModel.clearObservables()
    }

    fun setResponseData(response: MovieList) {
        if (response.results == null
            || currentPage == response.total_pages
        ) {
            isLastPage = true
        }
        isLoading = false
        response.results?.let { handleResponse(it) }
    }

    /***
     * Method for pagination while scroll list
     * **/
    private var recyclerViewOnScrollListener: RecyclerView.OnScrollListener =
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                mDataBinding?.rvMovieList?.layoutManager?.let { layoutManager ->
                    var visibleCount: Int = layoutManager.getChildCount();
                    var totalItemCount: Int = layoutManager.getItemCount();
                    var firstVisibleItemPosition: Int =
                        (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition();

                    if (!isLoading && !isLastPage
                        && ((visibleCount + firstVisibleItemPosition) >= totalItemCount
                                && firstVisibleItemPosition >= 0)
                    ) {
                        isLoading = true
                        fetchMovieList()
                    }

                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            }
        }

    fun hideProgressView() {
        mDataBinding.progressBar.visibility = View.INVISIBLE
    }

    fun showProgressView() {
        mDataBinding.progressBar.visibility = View.VISIBLE
    }
}