package com.example.githubjobs.data

import androidx.room.Room
import com.example.githubjobs.data.local.AppDatabase
import com.example.githubjobs.data.remote.ApiProvider
import com.example.githubjobs.data.remote.api.PositionAPI
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module

val dataModule = module {
    // Singleton for the DB
    single { Room.databaseBuilder(androidApplication(), AppDatabase::class.java, AppDatabase.DB_NAME).build() }
    // Singleton for the dao
    single {
        // Resolve the DB dependency 1st
        get<AppDatabase>().getPositionDAO()
    }
    // Provide an implementation for the repo interface
    single<PositionsRepository> { PositionsRepositoryImpl() }

    single { ApiProvider() }

    single { get<ApiProvider>().createAPI(PositionAPI::class.java) }

}