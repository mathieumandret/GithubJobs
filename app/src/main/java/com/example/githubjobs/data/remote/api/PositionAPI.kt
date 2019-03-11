package com.example.githubjobs.data.remote.api

import com.example.githubjobs.data.remote.model.PositionResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface PositionAPI {

    @GET("positions.json")
    fun getAllPositions(): Single<List<PositionResponse>>

    @GET("positions/{id}.json")
    fun getPositionById(@Path("id") id: String): Single<PositionResponse>
}