package ru.androidschool.intensiv.extensions

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.androidschool.intensiv.data.MovieResponse

fun Single<MovieResponse>.extensionsForObservable(): Single<MovieResponse> {
    return this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}
