package ru.androidschool.intensiv.extensions

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.androidschool.intensiv.data.CastDetailsResponse
import ru.androidschool.intensiv.data.CastResponse
import ru.androidschool.intensiv.data.MovieDetailsResponse
import ru.androidschool.intensiv.data.MovieResponse

fun Single<MovieResponse>.extensionsForObservable(): Single<MovieResponse> {
    return this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun Single<MovieDetailsResponse>.extensionsMovieDetailsForObservable(): Single<MovieDetailsResponse> {
    return this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun Single<CastDetailsResponse>.extensionsCastDetailsForObservable(): Single<CastDetailsResponse> {
    return this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}
