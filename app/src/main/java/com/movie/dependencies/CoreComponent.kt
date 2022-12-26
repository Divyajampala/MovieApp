package com.movie.dependencies

import com.movie.view.MovieDetailedActivity
import com.movie.view.MovieListFragment
import com.movie.view.SearchFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        ImageModule::class,
        DispatcherModule::class]
)
interface CoreComponent {

    fun inject(movieListFragment: MovieListFragment)
    fun inject(searchFragment: SearchFragment)
    fun inject(movieDetailedActivity: MovieDetailedActivity)
}
