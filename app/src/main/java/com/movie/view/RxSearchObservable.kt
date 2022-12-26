package com.movie.view

import androidx.appcompat.widget.SearchView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

object RxSearchObservable {
    fun fromView(searchView: SearchView): Observable<String> {
        val subject = PublishSubject.create<String>()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                subject.onComplete()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { subject.onNext(it) }
                return true
            }
        })
        return subject
    }
}