package com.global.test.globaltest.ui

import android.databinding.BaseObservable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject

open class BaseViewModel : BaseObservable() {

    private val disposable = CompositeDisposable()

    val progressSubject = PublishSubject.create<Boolean>()

    fun showProgress() = progressSubject.onNext(true)
    fun hideProgress() = progressSubject.onNext(false)


    protected fun addReaction(reaction: Disposable) {
        disposable.add(reaction)
    }

    fun onStop() {
        // clear all the subscriptions
        disposable.clear()
    }
}
