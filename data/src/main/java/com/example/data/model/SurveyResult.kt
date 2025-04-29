package com.example.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SurveyResult(
    val title: String,
    val result: List<QuestionAndAnswer>
)