package com.example.data.mapper

import com.example.data.model.QuestionAndAnswer
import com.example.domain.model.QuestionAndAnswer as QuestionAndAnswerDomain

fun QuestionAndAnswer.toDomain() = QuestionAndAnswerDomain(
    question = this.question.toDomain(),
    answer = this.answer.toDomain()
)

fun QuestionAndAnswerDomain.toData() = QuestionAndAnswer(
    question = this.question.toData(),
    answer = this.answer.toData()
)