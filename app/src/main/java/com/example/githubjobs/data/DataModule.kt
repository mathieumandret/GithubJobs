package com.example.githubjobs.data

import androidx.room.Room
import com.example.githubjobs.data.local.AppDatabase
import com.example.githubjobs.data.remote.ApiProvider
import com.example.githubjobs.data.remote.api.PositionAPI
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module

val dataModule = module {
    // Singleton pour la BDD
    single { Room.databaseBuilder(androidApplication(), AppDatabase::class.java, AppDatabase.DB_NAME).build() }
    // Singleton pour le DAO
    single {
        // Résoudre la BDD avant de pouvoir avoir le DAO
        get<AppDatabase>().getPositionDAO()
    }
    // Choix de l'implementation pour l'interface PositionsRepository
    single<PositionsRepository> { PositionsRepositoryImpl() }

    single { ApiProvider() }

    single { get<ApiProvider>().createAPI(PositionAPI::class.java) }

}