package com.global.test.globaltest.ui

import android.databinding.BaseObservable
import io.reactivex.subjects.PublishSubject

open class BaseViewModel : BaseObservable() {

    val progressSubject = PublishSubject.create<Boolean>()

    fun showProgress() = progressSubject.onNext(true)
    fun hideProgress() = progressSubject.onNext(false)

}
