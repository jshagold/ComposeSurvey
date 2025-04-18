package com.example.composesurvey.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Survey(
    val title: String,
    val questions: List<Question>
)