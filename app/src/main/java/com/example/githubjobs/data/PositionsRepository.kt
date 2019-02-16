package com.example.githubjobs.data

import com.example.githubjobs.data.local.model.Position
import io.reactivex.Single

interface PositionsRepository {

    fun getAllPositions(): Single<List<Position>>

    fun getPositionById(id: String): Single<Position>

    fun findPositionsByDescription(description: String): Single<List<Position>>

    fun findPositionsByLocation(location: String): Single<List<Position>>

    fun findPositionsByLocationAndDescription(location: String, description: String): Single<List<Position>>
}