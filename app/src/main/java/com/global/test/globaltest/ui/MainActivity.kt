package com.global.test.globaltest.ui

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.widget.Toast
import com.global.test.globaltest.R
import com.global.test.globaltest.databinding.ActivityMainBinding
import com.global.test.globaltest.network.WebClient
import com.global.test.globaltest.repositories.DataRepositoryImpl
import com.global.test.globaltest.uiSubscribe

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createViewBinding(this, R.layout.activity_main)

        viewModel = MainViewModel(DataRepositoryImpl(WebClient().dataService()))
        viewBinding.viewmodel = viewModel
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
}
