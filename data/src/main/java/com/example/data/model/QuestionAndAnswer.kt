package com.example.data.model

import kotlinx.serialization.Serializable

@Serializable
data class QuestionAndAnswer(
    val question: Question,
    val answer: Answer
)