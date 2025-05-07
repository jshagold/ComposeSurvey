package com.example.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Question(
    val id: Long,
    val surveyId: Long = 0,
    val type: QuestionType,
    val question: String,
    val required: Boolean = false,
    val options: List<String>? = null, // single_choice, multiple_choice
    val min: Int? = null, // slider
    val max: Int? = null, // slider
    val scaleList: List<String>? = null, // likert_scale
)