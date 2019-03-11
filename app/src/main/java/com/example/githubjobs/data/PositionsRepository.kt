package com.example.githubjobs.data

import androidx.lifecycle.LiveData
import com.example.githubjobs.data.local.model.Position
import io.reactivex.Completable

interface PositionsRepository {

    fun downloadPositions(): Completable

    fun getAllPositions(): LiveData<List<Position>>

    fun getPositionById(id: String): LiveData<Position>

    fun findPositionByTitle(title: String): LiveData<List<Position>>

    fun update(position: Position)
}