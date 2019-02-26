package com.global.test.globaltest.repositories

import com.global.test.globaltest.model.CodeData
import com.global.test.globaltest.model.PathData
import io.reactivex.Observable

interface DataRepository {

    fun fetchPath(): Observable<PathData>

    fun fetchCode(path: String): Observable<CodeData>
}