package com.movie.dependencies

import com.movie.MovieActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        ImageModule::class,
        DispatcherModule::class]
)
interface CoreComponent {

    fun inject(movieActivity :  MovieActivity)
}
