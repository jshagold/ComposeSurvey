package com.example.composesurvey.mapper

import com.example.composesurvey.model.SurveyUI
import com.example.domain.model.Survey
import com.example.domain.model.SurveyPreview

fun SurveyPreview.toUI() = SurveyUI(
    id = 0L, // todo
    title = this.title,
    description = "" // todo
)

fun Survey.toUI() = SurveyUI(
    id = this.id,
    title = this.title,
    description = this.description
)

fun SurveyUI.toDomain() = Survey(
    id = this.id,
    title = this.title,
    description = this.description,
    questions = listOf() // todo
)