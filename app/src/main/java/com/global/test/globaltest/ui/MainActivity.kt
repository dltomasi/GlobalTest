package com.global.test.globaltest.ui

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.global.test.globaltest.R
import com.global.test.globaltest.databinding.ActivityMainBinding
import com.global.test.globaltest.network.WebClient
import com.global.test.globaltest.repositories.DataRepositoryImpl
import com.global.test.globaltest.repositories.SharedPreferencesRepository
import com.global.test.globaltest.uiSubscribe

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createViewBinding(this, R.layout.activity_main)

        viewModel = MainViewModel(DataRepositoryImpl(WebClient().dataService()), SharedPreferencesRepository(getPreferences(Context.MODE_PRIVATE)))
        viewBinding.viewmodel = viewModel

        addObservers()
    }

    override fun onStart() {
        super.onStart()

        addReaction(viewModel.error
            .uiSubscribe()
            .subscribe {
                if (it != null) {
                    Toast.makeText(baseContext, it, Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun addObservers() {
        codeObserver()
        timesObserver()
    }

    private fun codeObserver() {
        val codeObserver = Observer<String> { code ->
            viewBinding.responseCode.text = code
        }

        viewModel.code.observe(this, codeObserver)
    }

    private fun timesObserver() {
        val timesObserver = Observer<Int> { times ->
            viewBinding.timesFetched.text = times.toString()
        }

        viewModel.timesFetched.observe(this, timesObserver)
    }
}
