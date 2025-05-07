package com.example.data.datasource

import com.example.data.model.Answer
import com.example.data.model.Question
import com.example.data.model.QuestionAndAnswer
import com.example.data.model.Survey

interface SurveyDBDataSource {

    suspend fun getSurvey(surveyId: Long): Survey?

    suspend fun getSurveyList(): List<Survey>

//    fun getSurveyTitleList(): List<Result<SurveyPreview>>

    suspend fun saveSurveyWithQuestions(survey: Survey)

    fun saveSurvey(survey: Survey)

    fun saveQuestion(question: Question)

    suspend fun saveAnswerList(answer: List<QuestionAndAnswer>)
}
