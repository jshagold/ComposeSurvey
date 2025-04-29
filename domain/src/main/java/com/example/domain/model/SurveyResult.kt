package com.example.domain.model

data class SurveyResult(
    val title: String,
    val result: List<QuestionAndAnswer>
)