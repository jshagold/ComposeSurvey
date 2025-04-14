package com.example.composesurvey.data

import com.example.composesurvey.model.Survey
import com.example.composesurvey.model.SurveyResult

interface SurveyRepository {

    fun getSurvey(fileName: String): Survey?

    fun getSurveyTitleList(): List<String>

    fun getSurveyList(): List<Survey>

    fun exportSurveyResult(result: SurveyResult)

}