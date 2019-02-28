package com.global.test.globaltest.network

import com.global.test.globaltest.model.CodeData
import com.global.test.globaltest.model.PathData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface DataService {

    @GET("./")
    fun fetchPath() : Observable<PathData>

    @GET("{nextPath}")
    fun fetchCode(@Path("nextPath", encoded = false) nextPath: String?) : Observable<CodeData>

}