package com.example.data.datasource

import com.example.core.result.Result
import com.example.domain.model.Survey
import com.example.domain.model.SurveyPreview

interface SurveyFileDataSource {

    fun getSurvey(fileName: String): Survey

    fun getSurveyTitleList(): List<Result<SurveyPreview>>

    fun getSurveyList(): List<Survey>
}