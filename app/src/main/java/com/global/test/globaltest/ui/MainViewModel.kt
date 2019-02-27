package com.global.test.globaltest.ui

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.global.test.globaltest.backgroundSubscribe
import com.global.test.globaltest.repositories.DataRepository

class MainViewModel(private val repository: DataRepository) : BaseObservable() {

    var code: String = ""
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

    fun fetchCode() {
        repository.fetchPath()
            .map { fetchCode(it.next_path) }
            .backgroundSubscribe()
            .subscribe(
                { },
                { e -> e.printStackTrace() })
    }

    private fun fetchCode(nextPath: String?) {
        if (nextPath != null) {
            repository.fetchCode(nextPath)
                .backgroundSubscribe()
                .subscribe(
                    {
                        code = it.response_code!!
                        timesCount++
                    },
                    { e -> e.printStackTrace() })
        }
    }
}