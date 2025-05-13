package com.example.domain.repository

import com.example.core.result.Result
import com.example.domain.model.Question
import com.example.domain.model.QuestionWithAnswer
import com.example.domain.model.Survey
import com.example.domain.model.SurveyPreview
import com.example.domain.model.SurveyResult

interface SurveyRepository {

    suspend fun getSurvey(surveyId: Long): Survey?

    fun getSurvey(fileName: String): Survey

    fun getSurveyTitleList(): List<Result<SurveyPreview>>

    suspend fun getSurveyList(): List<Result<Survey>>

    suspend fun saveSurveyToDBFromFile(): Result<String>

    suspend fun saveSurveyResult(result: List<QuestionWithAnswer>)

    fun exportSurveyResult(result: SurveyResult)

    /**
     * Text - List(Answer)
     * Single Choice - List(Element, Count)
     * Multiple Choice - List(Element, Count)
     * Slide - List(Value, Count)
     * Likert Scale - List(Value, Count)
     */
    suspend fun getStatisticsDataFromSurvey(surveyId: Long): LinkedHashMap<Question, Map<String, Int>>

    suspend fun getQuestionWithAnswerListBySurveyId(surveyId: Long): List<QuestionWithAnswer>



}