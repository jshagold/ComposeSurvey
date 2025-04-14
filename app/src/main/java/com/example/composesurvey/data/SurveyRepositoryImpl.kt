package com.example.composesurvey.data

import com.example.composesurvey.model.Survey
import com.example.composesurvey.model.SurveyResult
import javax.inject.Inject

class SurveyRepositoryImpl @Inject constructor(
    val surveyFileDataSource: SurveyFileDataSource
) : SurveyRepository {

    override fun getSurveyList(): List<Survey> {
        return surveyFileDataSource.loadSurveyList()
    }

    override fun exportSurveyResult(result: SurveyResult) {
        TODO("Not yet implemented")
    }

}