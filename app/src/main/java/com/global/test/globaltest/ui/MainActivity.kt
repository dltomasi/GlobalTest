package com.global.test.globaltest.ui

import android.os.Bundle
import com.global.test.globaltest.R
import com.global.test.globaltest.databinding.ActivityMainBinding
import com.global.test.globaltest.network.WebClient
import com.global.test.globaltest.repositories.DataRepositoryImpl

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createViewBinding(this, R.layout.activity_main)

        viewModel = MainViewModel(DataRepositoryImpl(WebClient().dataService()))
        viewBinding.viewmodel = viewModel
    }

}
