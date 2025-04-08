package com.example.composesurvey.model

import kotlinx.serialization.Serializable

@Serializable
data class Question(
    val id: String,
    val type: QuestionType,
    val question: String,
    val required: Boolean = false,
    val options: List<String>? = null, // single_choice, multiple_choice
    val min: Int? = null, // slider
    val max: Int? = null, // slider
    val scale0: String? = null, // likert_scale
    val scale1: String? = null, // likert_scale
    val scale2: String? = null, // likert_scale
    val scale3: String? = null, // likert_scale
    val scale4: String? = null, // likert_scale
)
