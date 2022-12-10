package com.movie.services

import io.reactivex.Single
import retrofit2.Response

interface APIService {

    fun getMovieList() : Single<Response<Double>>
}
