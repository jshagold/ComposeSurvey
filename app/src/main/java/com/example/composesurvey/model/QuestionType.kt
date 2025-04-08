package com.example.composesurvey.model

import kotlinx.serialization.Serializable

@Serializable
enum class QuestionType {
    TEXT,
    SINGLE_CHOICE,
    MULTIPLE_CHOICE,
    SLIDER,
    LIKERT_SCALE,
}