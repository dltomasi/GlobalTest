package com.global.test.globaltest.repositories

import android.content.SharedPreferences

class SharedPreferencesRepository(private val persistence: SharedPreferences) : LocalRepository {

    val TIMES_KEY = "TIMES_KEY"
    val CODE_KEY = "CODE_KEY"


    override fun saveData(times: Int, code: String) {
        persistence.edit()
            .putInt(TIMES_KEY, times)
            .putString(CODE_KEY, code)
            .apply()
    }

    override fun getTimes(): Int {
        return persistence.getInt(TIMES_KEY, 0)
    }

    override fun getCode(): String? {
        return persistence.getString(CODE_KEY, null);
    }

}