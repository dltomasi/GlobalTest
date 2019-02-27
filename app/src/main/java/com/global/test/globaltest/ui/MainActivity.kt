package com.global.test.globaltest.ui

import android.databinding.DataBindingUtil
import android.databinding.DataBindingUtil.setContentView
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.global.test.globaltest.R
import com.global.test.globaltest.databinding.ActivityMainBinding
import com.global.test.globaltest.network.WebClient
import com.global.test.globaltest.repositories.DataRepository
import com.global.test.globaltest.repositories.DataRepositoryImpl

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding : ActivityMainBinding = setContentView(this, R.layout.activity_main)

        viewModel = MainViewModel(DataRepositoryImpl(WebClient().dataService()))
        viewBinding.viewmodel = viewModel

    }

}
