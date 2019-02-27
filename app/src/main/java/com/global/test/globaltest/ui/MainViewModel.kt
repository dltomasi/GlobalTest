package com.global.test.globaltest.ui

import android.databinding.Bindable
import com.global.test.globaltest.backgroundSubscribe
import com.global.test.globaltest.repositories.DataRepository
import com.global.test.globaltest.repositories.LocalRepository
import io.reactivex.subjects.BehaviorSubject

class MainViewModel(private val dataRepository: DataRepository,
                    private val localRepository: LocalRepository) : BaseViewModel() {

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

    private var timesCount = 0
        @Bindable set(value) {
            field = value
            times = Integer.toString(value)
        }

    var error = BehaviorSubject.create<String>()

    init {
        timesCount = localRepository.getTimes()
        code = localRepository.getCode()
    }

    fun fetchCode() {
        addReaction(
        dataRepository.fetchPath()
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
            dataRepository.fetchCode(nextPath)
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
                    localRepository.saveData(timesCount, code!!)
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