package com.global.test.globaltest.repositories

import com.global.test.globaltest.model.CodeData
import com.global.test.globaltest.model.PathData
import io.reactivex.Observable

interface LocalRepository {

    fun saveData(times: Int, code: String)

    fun getTimes() : Int

    fun getCode() : String?
}