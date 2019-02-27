package com.global.test.globaltest.ui

import android.databinding.Bindable
import com.global.test.globaltest.backgroundSubscribe
import com.global.test.globaltest.repositories.DataRepository
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class MainViewModel(private val repository: DataRepository) : BaseViewModel() {

    var code: String? = null
        @Bindable set(value) {
            field = value
            notifyChange()
        }

    var times: String = "0"
        @Bindable set(value) {
            field = value
            notifyChange()
        }

    var timesCount = 0
        @Bindable set(value) {
            field = value
            times = Integer.toString(value)
        }

    var error = BehaviorSubject.create<String>()

    init {
        // get count and code from local
    }

    fun fetchCode() {
        addReaction(
        repository.fetchPath()
            .map { fetchCode(it.next_path) }
            .backgroundSubscribe()
            .doOnSubscribe { showProgress() }
            .doOnComplete { hideProgress() }
            .subscribe({},
                { e -> handleError(e) }))
    }

    private fun fetchCode(nextPath: String?) {
        if (nextPath != null) {
            addReaction(
            repository.fetchCode(nextPath)
                .backgroundSubscribe()
                .doOnSubscribe { showProgress() }
                .doOnComplete { hideProgress() }
                .doOnNext {
                    if (!it.isSuccess()) {
                        handleError(it.error!!)
                        return@doOnNext
                    }

                    code = it.response_code
                    timesCount++
                }
                //.doOnError { e -> error = e.localizedMessage }
                .subscribe({},
                    { e -> handleError(e) }))
        }
    }

    private fun handleError(e: Throwable) {
        handleError(e.localizedMessage)
    }

    private fun handleError(message: String) {
        hideProgress()
        error.onNext(message)
    }
}