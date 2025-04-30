package com.example.domain.repository

import com.example.core.result.Result
import com.example.domain.model.Survey
import com.example.domain.model.SurveyPreview
import com.example.domain.model.SurveyResult

interface SurveyRepository {

    fun getSurvey(surveyId: Long): Survey

    fun getSurvey(fileName: String): Survey

    fun getSurveyTitleList(): List<Result<SurveyPreview>>

    fun getSurveyList(): List<Survey>

    fun exportSurveyResult(result: SurveyResult)

}