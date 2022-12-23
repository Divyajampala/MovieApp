package com.movie.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.movie.BuildConfig
import com.movie.model.Movie
import com.movie.model.MovieList
import com.movie.services.APIService
import com.movie.services.MovieCategory
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class MovieViewModel(var apiService: APIService) : RxjavaViewModel() {

    val movieListResponse = MutableLiveData<MovieList>()

    fun fetchMovieList(
        page: Int,
        category: MovieCategory
    ) {
        apiService.getMovieList(category.toString(), BuildConfig.API_KEY, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<MovieList> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onSuccess(t: MovieList) {
                    movieListResponse.postValue(t)
                }

                override fun onError(e: Throwable) {
                }

            })
    }
}


class MovieViewModelFactory
@Inject constructor(var apiService: APIService) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieViewModel(apiService) as T
    }
}