package com.example.composesurvey.data

import com.example.composesurvey.common.result.Result
import com.example.composesurvey.data.model.Survey
import com.example.composesurvey.data.model.SurveyPreview
import com.example.composesurvey.data.model.SurveyResult

interface SurveyRepository {

    fun getSurvey(fileName: String): Survey

    fun getSurveyTitleList(): List<Result<SurveyPreview>>

    fun getSurveyList(): List<Survey>

    fun exportSurveyResult(result: SurveyResult)

}