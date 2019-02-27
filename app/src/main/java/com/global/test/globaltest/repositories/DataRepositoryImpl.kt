package com.global.test.globaltest.repositories

import com.global.test.globaltest.model.CodeData
import com.global.test.globaltest.model.PathData
import com.global.test.globaltest.model.Response
import com.global.test.globaltest.network.DataService
import com.global.test.globaltest.network.WebClient
import io.reactivex.Observable

class DataRepositoryImpl(private val dataService: DataService) : DataRepository {

    override fun fetchPath(): Observable<PathData> {
        return dataService.fetchPath()
    }

    override fun fetchCode(path : String): Observable<CodeData> {
        val formattedPath = path.substring(WebClient.host.length, path.length)
        return dataService.fetchCode(formattedPath)
    }
}