package com.example.composesurvey.view.model

import com.example.composesurvey.data.model.QuestionType

data class QuestionUI(
    val id: String,
    val type: QuestionType,
    val question: String,
    val required: Boolean = false,
    val options: List<String>? = null, // single_choice, multiple_choice
    val min: Int? = null, // slider
    val max: Int? = null, // slider
    val scaleList: List<String>? = null, // likert_scale
)