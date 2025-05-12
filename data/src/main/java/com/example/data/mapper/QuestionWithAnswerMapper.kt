package com.example.data.mapper

import com.example.data.model.QuestionWithAnswer
import com.example.domain.model.QuestionWithAnswer as QuestionWithAnswerDomain

fun QuestionWithAnswer.toDomain() = QuestionWithAnswerDomain(
    question = this.question.toDomain(),
    answer = this.answer.toDomain()
)

fun QuestionWithAnswerDomain.toData() = QuestionWithAnswer(
    question = this.question.toData(),
    answer = this.answer.toData()
)