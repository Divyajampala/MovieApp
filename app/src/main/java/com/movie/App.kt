package com.movie

import android.app.Application
import com.movie.dependencies.*

class App : Application() {

    val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.builder()
            .appModule(AppModule(this))
            .networkModule(NetworkModule())
            .databaseModule(DatabaseModule())
            .build()
    }
}