package com.example.githubjobs.data.remote

import com.example.githubjobs.util.API_URL
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiProvider {

    private val retrofit = Retrofit
        .Builder()
        .baseUrl(API_URL)
        .client(buildHttpClient())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private fun buildHttpClient(): OkHttpClient = OkHttpClient()


    fun <T> createAPI(fromClass: Class<T>): T = retrofit.create(fromClass)
}