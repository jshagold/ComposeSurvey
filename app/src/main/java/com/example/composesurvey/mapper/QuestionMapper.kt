package com.example.composesurvey.mapper

import com.example.composesurvey.model.QuestionUI
import com.example.domain.model.Question

fun QuestionUI.toDomain() = Question(
    id = this.id,
    surveyId = this.surveyId,
    type = this.type.toDomain(),
    question = this.question,
    required = this.required,
    options = this.options,
    min = this.min,
    max = this.max,
    scaleList = this.scaleList
)

fun Question.toUI() = QuestionUI(
    id = this.id,
    surveyId = this.surveyId,
    type = this.type.toUI(),
    question = this.question,
    required = this.required,
    options = this.options,
    min = this.min,
    max = this.max,
    scaleList = this.scaleList
)