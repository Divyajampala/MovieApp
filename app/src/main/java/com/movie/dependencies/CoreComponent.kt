package com.movie.dependencies

import com.movie.view.MovieActivity
import com.movie.view.MovieDetailedActivity
import com.movie.view.MovieListFragment
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

    fun inject(movieActivity: MovieActivity)
    fun inject(movieListFragment: MovieListFragment)
    fun inject(movieDetailedActivity: MovieDetailedActivity)
}
