package com.example.githubjobs.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "position")
data class Position(
    @PrimaryKey
    val id: String,
    val type: String,
    val url: String,
    @ColumnInfo(name = "created_at")
    val createdAt: Date,
    val company: String,
    @ColumnInfo(name = "company_url")
    val companyURL: String?,
    val location: String,
    val title: String,
    val description: String,
    @ColumnInfo(name = "how_to_apply")
    val howToApply: String,
    @ColumnInfo(name = "company_logo_url")
    val companyLogoURL: String?
)