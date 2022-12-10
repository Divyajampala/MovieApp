package com.movie.viewModel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class RxjavaViewModel(protected val compositeDisposable: CompositeDisposable = CompositeDisposable()) : ViewModel() {

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun clearObservables() {
        compositeDisposable.clear()
        compositeDisposable.dispose()
    }
}