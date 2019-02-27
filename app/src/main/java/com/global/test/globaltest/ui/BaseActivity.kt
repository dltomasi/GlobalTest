package com.global.test.globaltest.ui

import android.app.Activity
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ProgressBar
import com.global.test.globaltest.uiSubscribe
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseActivity<M : BaseViewModel, B : ViewDataBinding> : AppCompatActivity() {

    private val disposable = CompositeDisposable()
    protected lateinit var progress: ProgressBar
    protected lateinit var viewModel: M
    protected lateinit var viewBinding: B

    protected fun createViewBinding(activity: Activity, @LayoutRes layoutRes: Int) {
        viewBinding = DataBindingUtil.setContentView(activity, layoutRes)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progress = ProgressBar(this)
        setProgress(false)
    }

    protected fun addReaction(reaction: Disposable) {
        disposable.add(reaction)
    }

    override fun onStart() {
        super.onStart()
        addContentView(progress, ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        addReaction(viewModel.progressSubject
                .uiSubscribe()
                .subscribe({ show -> setProgress(show) }))
    }

    override fun onStop() {
        super.onStop()
        // clear all the subscriptions
        disposable.clear()
    }

    protected fun setProgress(show: Boolean) {
        progress.visibility = if (show) VISIBLE else GONE
    }
}