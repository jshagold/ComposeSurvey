package com.example.data.mapper

import com.example.data.model.Question
import com.example.domain.model.Question as QuestionDomain

fun Question.toDomain() = QuestionDomain(
    id = this.id,
    type = this.type.toDomain(),
    question = this.question,
    required = this.required,
    options = this.options,
    min = this.min,
    max = this.max,
    scaleList = this.scaleList
)

fun QuestionDomain.toData() = Question(
    id = this.id,
    type = this.type.toData(),
    question = this.question,
    required = this.required,
    options = this.options,
    min = this.min,
    max = this.max,
    scaleList = this.scaleList
)