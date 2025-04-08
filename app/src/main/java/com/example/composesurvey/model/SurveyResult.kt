package com.example.composesurvey.model

import kotlinx.serialization.Serializable

@Serializable
data class SurveyResult(
    val title: String,
    val result: List<Pair<Question, Answer>>
)