package com.example.data.datasource

import com.example.data.model.Answer
import com.example.data.model.Question
import com.example.data.model.QuestionWithAnswer
import com.example.data.model.Survey

interface SurveyDBDataSource {

    suspend fun getSurvey(surveyId: Long): Survey?

    suspend fun getSurveyList(): List<Survey>

    suspend fun isExistSurvey(title: String): Boolean

    suspend fun saveSurveyWithQuestions(survey: Survey)

    suspend fun saveSurveyWithQuestionsIfNew(survey: Survey)

    fun saveSurvey(survey: Survey)

    fun saveQuestion(question: Question)

    suspend fun saveAnswerList(answer: List<QuestionWithAnswer>)

    suspend fun getQuestionIdListBySurveyId(surveyId: Long): List<Long>

    suspend fun getQuestionListBySurveyId(surveyId: Long): List<Question>

    suspend fun getQuestionWithAnswerListBySurveyId(surveyId: Long): List<QuestionWithAnswer>

    suspend fun getAnswerListBySurveyNQuestionId(surveyId: Long, questionId: Long): List<Answer>
}
