package com.movie.services

import com.movie.model.Movie
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("/3/movie/550")
    fun getMovieList(@Query("api_key") page: String): Single<Movie>
}
