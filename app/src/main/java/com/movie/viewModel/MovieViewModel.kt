package com.movie.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.movie.BuildConfig
import com.movie.model.*
import com.movie.services.APIService
import com.movie.services.MovieCategory
import com.nmc.myapplication.db.dao.CacheDao
import com.nmc.myapplication.db.dao.DataName
import com.nmc.myapplication.db.dao.FavMovieDao
import com.nmc.myapplication.db.entity.Cache
import com.nmc.myapplication.db.entity.FavMovieCache
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class MovieViewModel(
    private var apiService: APIService,
    val dao: CacheDao,
    val favMovieCacheDao: FavMovieDao
) : RxjavaViewModel() {

    val movieListResponse = MutableLiveData<MovieList>()
    val movieDetailsResponse = MutableLiveData<Movie>()
    val genreResponse = MutableLiveData<List<Genre>>()
    val loadingState = MutableLiveData<NetworkState>()
    val favMovieState = MutableLiveData<Boolean>()

    fun getObjectFromString(jsonString: String): List<Genre> {
        val turnsType = object : TypeToken<List<Genre>>() {}.type
        val list = Gson().fromJson<List<Genre>>(jsonString, turnsType)
        return list;

    }

    fun stringFromObject(list: List<Genre>): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

    fun getGenreList() {
        viewModelScope.launch(Dispatchers.IO) {
            var genreList = dao.getByDataName(DataName.GENRE.toString())
            if (genreList.isNullOrEmpty()) {
                getGenreDetails()
            } else {
                genreResponse.postValue(getObjectFromString(genreList))
            }
        }
    }

    fun checkFavMovieState(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            favMovieState.postValue(favMovieCacheDao.exists(id))
        }
    }

    fun setFavMovieState(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            favMovieCacheDao.insert(FavMovieCache(id))
        }
    }

    fun unsetFavMovieState(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            favMovieCacheDao.delete(FavMovieCache(id))
        }
    }

    fun fetchMovieList(
        page: Int,
        category: MovieCategory
    ) {
        loadingState.postValue(NetworkState.LOADING)
        apiService.getMovieList(category.toString(), BuildConfig.API_KEY, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<MovieList> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onSuccess(t: MovieList) {
                    movieListResponse.postValue(t)
                    loadingState.postValue(NetworkState.SUCCESS)
                }

                override fun onError(e: Throwable) {
                    loadingState.postValue(NetworkState.ERROR)
                }

            })
    }

    fun getMovieDetails(id: Int) {
        loadingState.postValue(NetworkState.LOADING)
        apiService.getMovieDetails(id, BuildConfig.API_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Movie> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onSuccess(t: Movie) {
                    movieDetailsResponse.postValue(t)
                    loadingState.postValue(NetworkState.SUCCESS)
                }

                override fun onError(e: Throwable) {
                    loadingState.postValue(NetworkState.ERROR)
                }

            })
    }

    fun getGenreDetails() {
        apiService.getGenreList(BuildConfig.API_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<GenreResponse> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onSuccess(t: GenreResponse) {
                    t.genres?.let {
                        genreResponse.postValue(it)
                        dao.insert(Cache(DataName.GENRE.toString(), stringFromObject(it)))
                    }
                }

                override fun onError(e: Throwable) {
                }

            })
    }
}


class MovieViewModelFactory
@Inject constructor(
    var apiService: APIService,
    val dao: CacheDao,
    val favMovieDao: FavMovieDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieViewModel(apiService, dao, favMovieDao) as T
    }
}