package ru.androidschool.intensiv.extensions

import android.view.View
import android.widget.ProgressBar
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.applySchedulers(scheduler: Scheduler = Schedulers.io()): Single<T> {
    return this
        .subscribeOn(scheduler)
        .observeOn(AndroidSchedulers.mainThread())
}

fun Observable<String>.doOnSubscribe(): Observable<String> {
    return this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.setLoaderSingle(progressBar: ProgressBar): Single<T> {
    return this
        .doOnSubscribe { progressBar.visibility = View.VISIBLE }
        .doFinally { progressBar.visibility = View.INVISIBLE }
}

fun <T> Observable<T>.setLoaderForObservable(progressBar: ProgressBar): Observable<T> {
    return this
        .doOnSubscribe { progressBar.visibility = View.VISIBLE }
        .doFinally { progressBar.visibility = View.INVISIBLE }
}
