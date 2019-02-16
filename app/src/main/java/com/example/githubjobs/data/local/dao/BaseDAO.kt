package com.example.githubjobs.data.local.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

interface BaseDAO<T> {

    @Insert
    fun insert(entity: T)

    @Insert
    fun insertAll(entity: List<T>)

    @Delete
    fun delete(entity: T)

    @Update
    fun update(entity: T)

}