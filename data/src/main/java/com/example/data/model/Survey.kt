package com.example.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Survey(
    val id: Long,
    val title: String,
    val description: String,
    val questions: List<Question>
)