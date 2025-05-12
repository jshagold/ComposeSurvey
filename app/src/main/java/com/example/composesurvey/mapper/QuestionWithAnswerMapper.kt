package com.example.composesurvey.mapper

import com.example.composesurvey.model.QuestionWithAnswerUI
import com.example.domain.model.QuestionWithAnswer

fun QuestionWithAnswerUI.toDomain() = QuestionWithAnswer(
    question = this.question.toDomain(),
    answer = this.answer.toDomain(),
)

fun QuestionWithAnswer.toUI() = QuestionWithAnswerUI(
    question = this.question.toUI(),
    answer = this.answer.toUI()
)