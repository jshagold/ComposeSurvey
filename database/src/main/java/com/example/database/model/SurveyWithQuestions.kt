package com.example.database.model

import androidx.room.Embedded
import androidx.room.Relation

data class SurveyWithQuestions(
    @Embedded
    val survey: SurveyEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "surveyId"
    )
    val questions: List<QuestionEntity>
)
