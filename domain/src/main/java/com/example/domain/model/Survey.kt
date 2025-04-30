package com.example.domain.model

data class Survey(
    val id: Long,
    val title: String,
    val description: String,
    val questions: List<Question>
)