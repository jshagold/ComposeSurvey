package com.example.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "answer",
    foreignKeys = [
        ForeignKey(
            entity = SurveyEntity::class,
            parentColumns = ["id"],
            childColumns = ["surveyId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = QuestionEntity::class,
            parentColumns = ["id"],
            childColumns = ["questionId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
)
data class AnswerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val surveyId: Long,

    val questionId: Long,

    val answerGroupId: String,
    val answerValue: String,
    val answeredAt: Long = System.currentTimeMillis(),
)