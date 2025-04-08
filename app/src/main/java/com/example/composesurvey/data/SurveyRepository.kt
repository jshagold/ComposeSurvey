package com.example.composesurvey.data

import com.example.composesurvey.model.Answer
import com.example.composesurvey.model.Survey
import com.example.composesurvey.model.SurveyResult

interface SurveyRepository {

    fun getSurvey(): Survey

    fun exportSurveyResult(result: SurveyResult)

}