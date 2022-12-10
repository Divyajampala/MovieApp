package com.movie.dependencies

import com.movie.dispatcher.CoroutineDispatcherProvider
import com.movie.dispatcher.RealCoroutineDispatcherProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DispatcherModule {

    @Provides
    @Singleton
    fun providesCoroutineDispatcher(): CoroutineDispatcherProvider {
        return RealCoroutineDispatcherProvider()
    }
}
