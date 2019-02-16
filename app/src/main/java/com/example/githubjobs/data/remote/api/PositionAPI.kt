package com.example.githubjobs.data.remote.api

import com.example.githubjobs.data.remote.model.PositionResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PositionAPI {

    @GET("positions.json")
    fun getAllPositions(): Single<List<PositionResponse>>

    @GET("positions/{id}.json")
    fun getPositionById(@Path("id") id: String): Single<PositionResponse>

    @GET("positions.json")
    fun findPositionsByLocation(@Query("location") location: String): Single<List<PositionResponse>>

    @GET("positions.json")
    fun findPositionsByDescription(@Query("description") description: String): Single<List<PositionResponse>>

    @GET("positions.json")
    fun findPositionsByLocationAndDescription(
        @Query("location") location: String,
        @Query("description") description: String
    ): Single<List<PositionResponse>>

}