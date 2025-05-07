package com.example.composesurvey.model

data class QuestionUI(
    val id: Long,
    val surveyId: Long,
    val type: QuestionTypeUI,
    val question: String,
    val required: Boolean = false,
    val options: List<String>? = null, // single_choice, multiple_choice
    val min: Int? = null, // slider
    val max: Int? = null, // slider
    val scaleList: List<String>? = null, // likert_scale
)