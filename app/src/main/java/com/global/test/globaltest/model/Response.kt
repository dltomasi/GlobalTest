package com.global.test.globaltest.model

open class Response {

    var error : String? = null

    fun isSuccess() : Boolean {
        return error == null
    }

}
