package com.movie.services

import com.movie.model.Movie
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    @GET("/3/movie/{movie_id}")
    fun getMovieDetails(
        @Query("api_key") page: String,
        @Path("movie_id") movie_id: String
    ): Single<Movie>

    @GET("/3/movie/{category}")
    fun getMovieList(
        @Query("api_key") api_key: String, @Query("page") page: String,
        @Path("movie_id") movie_id: String
    ): Single<Movie>

}

enum class MovieCategory {
    now_playing,
    popular,
    top_rated,
    upcoming
}
