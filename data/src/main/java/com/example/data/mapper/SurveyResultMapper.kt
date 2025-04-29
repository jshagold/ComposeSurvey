package com.example.data.mapper

import com.example.data.model.SurveyResult
import com.example.domain.model.SurveyResult as SurveyResultDomain

fun SurveyResult.toDomain() = SurveyResultDomain(
    title = this.title,
    result = this.result.map { it.toDomain() }
)

fun SurveyResultDomain.toData() = SurveyResult(
    title = this.title,
    result = this.result.map { it.toData() }
)