package com.movie.services

import com.movie.model.GenreResponse
import com.movie.model.Movie
import com.movie.model.MovieList
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    @GET("/3/genre/movie/list")
    fun getGenreList(
        @Query("api_key") page: String
    ): Single<GenreResponse>

    @GET("/3/movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") page: String
    ): Single<Movie>

    @GET("/3/movie/{category}")
    fun getMovieList(
        @Path("category") category: String,
        @Query("api_key") api_key: String, @Query("page") page: Int,
    ): Single<MovieList>
}

enum class MovieCategory(val title: String) {
    now_playing("Now playing"),
    popular("Popular"),
    top_rated("Top Rated"),
    upcoming("Upcoming")
}
