package com.example.githubjobs.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.githubjobs.data.local.model.Position

@Dao
interface PositionDAO : BaseDAO<Position> {

    @Query("SELECT * FROM position")
    fun getAllPositions(): LiveData<List<Position>>

    @Query("SELECT * FROM position WHERE id = :id")
    fun getPositionById(id: String): LiveData<Position>

    @Query("SELECT * FROM position WHERE location LIKE :location")
    fun findPositionsByLocation(location: String): LiveData<List<Position>>

    @Query("SELECT * FROM position WHERE description LIKE :description")
    fun findPositionsByDescription(description: String): LiveData<List<Position>>

    @Query("SELECT * FROM position WHERE description LIKE :description AND location LIKE :location")
    fun findPositionsByLocationAndDescription(location: String, description: String): LiveData<List<Position>>
}
