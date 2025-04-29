package com.example.data.mapper

import com.example.data.model.Survey
import com.example.domain.model.Survey as SurveyDomain

fun Survey.toDomain() = SurveyDomain(
    title = this.title,
    questions = this.questions.map { it.toDomain() }
)

fun SurveyDomain.toData() = Survey(
    title = this.title,
    questions = this.questions.map { it.toData() }
)