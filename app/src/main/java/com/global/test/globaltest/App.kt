package com.global.test.globaltest

import android.app.Application
import com.global.test.globaltest.injection.DaggerDataComponent
import com.global.test.globaltest.injection.DataComponent
import com.global.test.globaltest.injection.DataModule


class App : Application() {

companion object {
    lateinit var component: DataComponent
}

    override fun onCreate() {
        super.onCreate();
        component = DaggerDataComponent
                .builder()
                .dataModule(DataModule(this))
                .build()
    }

}