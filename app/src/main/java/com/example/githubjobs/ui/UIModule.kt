package com.example.githubjobs.ui

import com.example.githubjobs.ui.positions.list.PositionListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val UIModule = module {
    viewModel { PositionListViewModel(androidApplication()) }
}

