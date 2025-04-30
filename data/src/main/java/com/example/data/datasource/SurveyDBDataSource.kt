package com.example.data.datasource

import com.example.core.result.Result
import com.example.data.model.Answer
import com.example.data.model.Question
import com.example.data.model.Survey

interface SurveyDBDataSource {

    fun getSurveyByFileName(fileName: String): Survey

    suspend fun getSurveyList(): List<Survey>

//    fun getSurveyTitleList(): List<Result<SurveyPreview>>

    fun saveSurvey(survey: Survey)

    fun saveQuestion(question: Question)

    fun saveAnswer(answer: Answer)
}
