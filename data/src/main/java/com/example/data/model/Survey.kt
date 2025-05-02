package com.example.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Survey(
    val id: Long = 0,
    val title: String,
    val description: String? = null,
    val questions: List<Question>
)