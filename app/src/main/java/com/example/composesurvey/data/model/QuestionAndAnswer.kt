package com.example.composesurvey.data.model

import com.example.composesurvey.data.model.Answer
import kotlinx.serialization.Serializable

@Serializable
data class QuestionAndAnswer(
    val question: Question,
    val answer: Answer
)