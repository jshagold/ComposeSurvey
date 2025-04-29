package com.example.composesurvey.mapper

import com.example.composesurvey.model.QuestionAndAnswerUI
import com.example.domain.model.QuestionAndAnswer

fun QuestionAndAnswerUI.toDomain() = QuestionAndAnswer(
    question = this.question.toDomain(),
    answer = this.answer.toDomain(),
)

fun QuestionAndAnswer.toUI() = QuestionAndAnswerUI(
    question = this.question.toUI(),
    answer = this.answer.toUI()
)