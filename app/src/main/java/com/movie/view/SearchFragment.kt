package com.movie.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.movie.App
import com.movie.adapter.MovieAdapter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment() : MovieListFragment() {

    private var query = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as App).coreComponent.inject(this)
    }

    override fun initView() {
        super.initView()
        mDataBinding.searchView.visibility = View.VISIBLE

        val disposable: Disposable = RxSearchObservable.fromView(mDataBinding.searchView)
            .debounce(1500, TimeUnit.MILLISECONDS)
            .filter { text -> !text.isEmpty() && text.length >= 3 }
            .map { text -> text.toLowerCase().trim() }
            .distinctUntilChanged()
            .switchMap { s -> Observable.just(s) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { query ->
                emptyList()
                this.query = query
                movieViewModel.getSearchResultFromApi(query, 1)
                currentPage = 1
            }
    }

    fun emptyList() {
        (mDataBinding.rvMovieList.adapter as MovieAdapter).apply {
            this.movieList.clear()
            notifyDataSetChanged()
        }
    }

    override fun fetchMovieList() {
        if (query.isNullOrEmpty().not())
            movieViewModel.getSearchResultFromApi(query, ++currentPage)
    }
}