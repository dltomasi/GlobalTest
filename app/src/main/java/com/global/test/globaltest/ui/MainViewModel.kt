package com.global.test.globaltest.ui

import android.arch.lifecycle.MutableLiveData
import com.global.test.globaltest.backgroundSubscribe
import com.global.test.globaltest.network.WebClient
import com.global.test.globaltest.repositories.DataRepository
import com.global.test.globaltest.repositories.LocalRepository
import com.global.test.globaltest.uiSubscribe
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class MainViewModel(
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
                .map { fetchCode(it.next_path) }
                .backgroundSubscribe()
                .doOnSubscribe { showProgress() }
                .subscribe({},
                    { e -> handleError(e) })
        )
    }

    private fun fetchCode(nextPath: String?) {
        if (nextPath != null) {
            addReaction(
                dataRepository.fetchCode(getPath(nextPath))
                    .uiSubscribe()
                    .delay(if (delay) 3L else 0, TimeUnit.SECONDS, Schedulers.trampoline())
                    .doOnComplete { hideProgress() }
                    .subscribe({
                        if (!it.isSuccess()) {
                            handleError(it.error!!)
                            return@subscribe
                        }

                        code.value = it.response_code
                        timesFetched.value = timesFetched.value?.plus(1)
                        if (code.value != null) {
                            localRepository.saveData(timesFetched.value!!, code.value!!)
                        }
                    },
                        { e -> handleError(e) })
            )
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