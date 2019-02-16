package com.example.githubjobs.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.githubjobs.data.local.converters.DateConverter
import com.example.githubjobs.data.local.dao.PositionDAO
import com.example.githubjobs.data.local.model.Position

@Database(
    entities = [Position::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DB_NAME = "githubjobs_db"
    }

    abstract fun getPositionDAO(): PositionDAO

}