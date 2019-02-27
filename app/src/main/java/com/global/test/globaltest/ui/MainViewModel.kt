package com.global.test.globaltest.ui

import android.databinding.Bindable
import android.os.Build
import android.support.annotation.RequiresApi
import com.global.test.globaltest.backgroundSubscribe
import com.global.test.globaltest.repositories.DataRepository

class MainViewModel(private val repository: DataRepository) : BaseViewModel() {

    var code: String? = ""
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

    init {
        // get count and code from local
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun fetchCode() {
        repository.fetchPath()
            .map { fetchCode(it.next_path) }
            .backgroundSubscribe()
            .doOnSubscribe { showProgress() }
            .doOnComplete { hideProgress() }
            .doOnError { e -> e.printStackTrace() }
            .subscribe()
    }

    private fun fetchCode(nextPath: String?) {
        if (nextPath != null) {
            repository.fetchCode(nextPath)
                .backgroundSubscribe()
                .doOnSubscribe { showProgress() }
                .doOnComplete { hideProgress() }
                .doOnNext {
                    code = it.response_code
                    timesCount++
                }
                .doOnError { e -> e.printStackTrace() }
                .subscribe()
        }
    }
}