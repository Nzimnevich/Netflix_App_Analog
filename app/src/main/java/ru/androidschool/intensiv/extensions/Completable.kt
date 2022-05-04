package ru.androidschool.intensiv.extensions

import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun Completable.extensionsForDB(scheduler: Scheduler = Schedulers.io()): Completable {
    return this
        .subscribeOn(scheduler)
        .observeOn(AndroidSchedulers.mainThread())
}