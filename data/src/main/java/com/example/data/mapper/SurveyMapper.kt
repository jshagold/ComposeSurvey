package com.example.data.mapper

import com.example.data.model.Survey
import com.example.database.model.SurveyEntity
import com.example.domain.model.Survey as SurveyDomain

fun Survey.toDomain() = SurveyDomain(
    id = this.id,
    title = this.title,
    description = this.description,
    questions = this.questions.map { it.toDomain() }
)

fun SurveyDomain.toData() = Survey(
    id = this.id,
    title = this.title,
    description = this.description,
    questions = this.questions.map { it.toData() }
)

fun Survey.toEntity() = SurveyEntity(
    title = this.title,
    description = this.description
)