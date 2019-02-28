package com.global.test.globaltest.injection

import com.global.test.globaltest.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class])
interface DataComponent {
    fun inject(activity: MainActivity)
}