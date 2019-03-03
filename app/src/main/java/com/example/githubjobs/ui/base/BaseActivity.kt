package com.example.githubjobs.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.example.githubjobs.BR
import org.koin.standalone.KoinComponent

abstract class BaseActivity<V : ViewModel, B : ViewDataBinding> : AppCompatActivity(), KoinComponent {

    protected abstract val layoutId: Int
    protected abstract val viewModel: V
    protected lateinit var binding: B


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpBinding()
    }

    private fun setUpBinding() {
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.setVariable(BR.viewModel, viewModel)
        binding.lifecycleOwner = this
    }
}