package com.example.domain.repository

import com.example.core.result.Result
import com.example.domain.model.QuestionAndAnswer
import com.example.domain.model.Survey
import com.example.domain.model.SurveyPreview
import com.example.domain.model.SurveyResult

interface SurveyRepository {

    suspend fun getSurvey(surveyId: Long): Survey?

    fun getSurvey(fileName: String): Survey

    fun getSurveyTitleList(): List<Result<SurveyPreview>>

    suspend fun getSurveyList(): List<Result<Survey>>

    suspend fun saveSurveyToDBFromFile(): Result<String>

    suspend fun saveSurveyResult(result: List<QuestionAndAnswer>)

    fun exportSurveyResult(result: SurveyResult)

}