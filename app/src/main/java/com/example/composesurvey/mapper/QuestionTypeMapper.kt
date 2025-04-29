package com.example.composesurvey.mapper

import com.example.composesurvey.model.QuestionTypeUI
import com.example.domain.model.QuestionType

fun QuestionTypeUI.toDomain(): QuestionType {
    return when(this) {
        QuestionTypeUI.TEXT -> QuestionType.TEXT
        QuestionTypeUI.SINGLE_CHOICE -> QuestionType.SINGLE_CHOICE
        QuestionTypeUI.MULTIPLE_CHOICE -> QuestionType.MULTIPLE_CHOICE
        QuestionTypeUI.SLIDER -> QuestionType.SLIDER
        QuestionTypeUI.LIKERT_SCALE -> QuestionType.LIKERT_SCALE
    }
}

fun QuestionType.toUI(): QuestionTypeUI {
    return when(this) {
        QuestionType.TEXT -> QuestionTypeUI.TEXT
        QuestionType.SINGLE_CHOICE -> QuestionTypeUI.SINGLE_CHOICE
        QuestionType.MULTIPLE_CHOICE -> QuestionTypeUI.MULTIPLE_CHOICE
        QuestionType.SLIDER -> QuestionTypeUI.SLIDER
        QuestionType.LIKERT_SCALE -> QuestionTypeUI.LIKERT_SCALE
    }
}