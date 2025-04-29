package com.example.data.mapper

import com.example.data.model.QuestionType
import com.example.domain.model.QuestionType as QuestionTypeDomain

fun QuestionType.toDomain(): QuestionTypeDomain {
    return when(this) {
        QuestionType.TEXT -> QuestionTypeDomain.TEXT
        QuestionType.SINGLE_CHOICE -> QuestionTypeDomain.SINGLE_CHOICE
        QuestionType.MULTIPLE_CHOICE -> QuestionTypeDomain.MULTIPLE_CHOICE
        QuestionType.SLIDER -> QuestionTypeDomain.SLIDER
        QuestionType.LIKERT_SCALE -> QuestionTypeDomain.LIKERT_SCALE
    }
}

fun QuestionTypeDomain.toData(): QuestionType {
    return when(this) {
        QuestionTypeDomain.TEXT -> QuestionType.TEXT
        QuestionTypeDomain.SINGLE_CHOICE -> QuestionType.SINGLE_CHOICE
        QuestionTypeDomain.MULTIPLE_CHOICE -> QuestionType.MULTIPLE_CHOICE
        QuestionTypeDomain.SLIDER -> QuestionType.SLIDER
        QuestionTypeDomain.LIKERT_SCALE -> QuestionType.LIKERT_SCALE
    }
}