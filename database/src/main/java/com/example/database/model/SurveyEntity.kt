package com.example.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "survey")
data class SurveyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    val title: String,
    val description: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
)