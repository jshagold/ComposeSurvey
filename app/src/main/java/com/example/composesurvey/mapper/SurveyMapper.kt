package com.example.composesurvey.mapper

import com.example.composesurvey.model.SurveyUI
import com.example.domain.model.SurveyPreview

fun SurveyPreview.toUI() = SurveyUI(
    surveyId = 0L, // todo
    title = this.title
)

fun SurveyUI.toDomain() = SurveyPreview(
    title = this.title,
    fileName = "" // todo
)