package com.example.githubjobs.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.githubjobs.data.local.model.Position
import io.reactivex.Single

@Dao
interface PositionDAO : BaseDAO<Position> {

    @Query("SELECT * FROM position")
    fun getAllPositions(): Single<List<Position>>

    @Query("SELECT * FROM position WHERE id = :id")
    fun getPositionById(id: String): Single<Position>

    @Query("SELECT * FROM position WHERE location LIKE :location")
    fun findPositionsByLocation(location: String): Single<List<Position>>

    @Query("SELECT * FROM position WHERE description LIKE :description")
    fun findPositionsByDescription(description: String): Single<List<Position>>

    @Query("SELECT * FROM position WHERE description LIKE :description AND location LIKE :location")
    fun findPositionsByLocationAndDescription(location: String, description: String): Single<List<Position>>
}
