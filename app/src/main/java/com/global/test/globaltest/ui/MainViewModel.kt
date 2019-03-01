package com.global.test.globaltest.ui

import android.arch.lifecycle.MutableLiveData
import com.global.test.globaltest.backgroundSubscribe
import com.global.test.globaltest.model.CodeData
import com.global.test.globaltest.network.WebClient
import com.global.test.globaltest.repositories.DataRepository
import com.global.test.globaltest.repositories.LocalRepository
import com.global.test.globaltest.uiSubscribe
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val localRepository: LocalRepository
) : BaseViewModel() {

    val code: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val timesFetched: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    var error = BehaviorSubject.create<String>()

    var delay = false

    init {
        timesFetched.value = localRepository.getTimes()
        code.value = localRepository.getCode()
    }

    fun fetchCode() {
        addReaction(
            dataRepository.fetchPath()
                .flatMap { fetchCode(it.next_path) }
                .backgroundSubscribe()
                .doOnSubscribe { showProgress() }
                .doOnComplete { hideProgress() }
                .subscribe({}, { handleError(it) })
        )
    }

    private fun fetchCode(nextPath: String?): Observable<CodeData>? {
        return dataRepository.fetchCode(getPath(nextPath))
            .uiSubscribe()
            .doOnNext { handleResponse(it) }
            .delay(if (delay) 3L else 0, TimeUnit.SECONDS, Schedulers.trampoline())
    }

    private fun handleResponse(it: CodeData) {
        if (!it.isSuccess()) {
            handleError(it.error!!)
            return
        }
        code.value = it.response_code
        timesFetched.value = timesFetched.value?.plus(1)
        if (code.value != null) {
            localRepository.saveData(timesFetched.value!!, code.value!!)
        }
    }

    private fun handleError(e: Throwable) {
        handleError(e.localizedMessage)
    }

    private fun handleError(message: String) {
        hideProgress()
        error.onNext(message)
    }

    private fun getPath(path: String?): String? {
        return path?.let {
            if (it.startsWith(WebClient.host)) {
                it.substring(WebClient.host.length, it.length)
            } else it
        }
    }
}