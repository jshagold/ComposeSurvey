package com.example.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "question",
    foreignKeys = [
        ForeignKey(
            entity = SurveyEntity::class,
            parentColumns = ["id"],
            childColumns = ["surveyId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class QuestionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    val surveyId: Long,

    val questionText: String,
    val type: String,
    val jsonOption: String? = null
)