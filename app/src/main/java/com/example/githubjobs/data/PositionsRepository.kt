package com.example.githubjobs.data

import androidx.lifecycle.LiveData
import com.example.githubjobs.data.local.model.Position
import io.reactivex.Completable

interface PositionsRepository {

    fun downloadPositions(): Completable

    fun getAllPositions(): LiveData<List<Position>>

    fun getPositionById(id: String): LiveData<Position>

    fun findPositionsByDescription(description: String): LiveData<List<Position>>

    fun findPositionByTitle(title: String): LiveData<List<Position>>

    fun findPositionsByLocation(location: String): LiveData<List<Position>>

    fun findPositionsByLocationAndDescription(location: String, description: String): LiveData<List<Position>>
}