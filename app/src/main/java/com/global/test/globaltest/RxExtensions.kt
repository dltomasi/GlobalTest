package com.global.test.globaltest

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

// example of kotlin extensions

fun <T> Observable<T>.uiSubscribe(): Observable<T> {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.backgroundSubscribe(): Observable<T> {
    return subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
}

fun Completable.uiSubscribe(): Completable {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}