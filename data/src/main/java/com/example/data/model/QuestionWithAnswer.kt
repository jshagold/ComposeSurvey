package com.example.data.model

import kotlinx.serialization.Serializable

@Serializable
data class QuestionWithAnswer(
    val question: Question,
    val answer: Answer
)