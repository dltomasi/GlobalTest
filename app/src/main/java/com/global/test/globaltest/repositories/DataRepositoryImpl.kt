package com.global.test.globaltest.repositories

import com.global.test.globaltest.model.CodeData
import com.global.test.globaltest.model.PathData
import com.global.test.globaltest.network.DataService
import io.reactivex.Observable

class DataRepositoryImpl(private val dataService: DataService) : DataRepository {

    override fun fetchPath(): Observable<PathData> {
        return dataService.fetchPath()
    }

    override fun fetchCode(path : String?): Observable<CodeData> {
        return dataService.fetchCode(path)
    }
}