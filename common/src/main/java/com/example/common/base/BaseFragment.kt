package com.example.common.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import lazyViewModel

abstract class BaseFragment<T: BaseViewModel>(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    private val _viewModel : BaseViewModel by lazyViewModel {
        createViewModel()
    }
    protected val viewModel by lazy {
        _viewModel as T
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    protected abstract fun inject()

    protected abstract fun createViewModel() : BaseViewModel

    protected abstract fun initViews()
}