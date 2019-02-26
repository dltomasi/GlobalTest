package com.global.test.globaltest.ui

import com.global.test.globaltest.model.PathData
import com.global.test.globaltest.repositories.DataRepository

class MainViewModel(private val repository: DataRepository) {

    fun fetchCode() {
        repository.fetchPath()
            .subscribe(
                { fetchCode(it.next_path) },
                { e -> e.stackTrace})
    }

    var code: String? = null

    private fun fetchCode(nextPath: String?) {
        if (nextPath != null) {
            repository.fetchCode(nextPath)
                .subscribe({ code = it.response_code},
                    {e -> e.stackTrace})
        }
    }
}