package com.example.composesurvey.data

import com.example.composesurvey.common.result.Result
import com.example.composesurvey.data.model.Survey
import com.example.composesurvey.data.model.SurveyPreview
import com.example.composesurvey.data.model.SurveyResult
import javax.inject.Inject

class SurveyRepositoryImpl @Inject constructor(
    val surveyFileDataSource: SurveyFileDataSource
) : SurveyRepository {
    override fun getSurvey(fileName: String): Survey {
        return surveyFileDataSource.getSurvey(fileName)
    }

    override fun getSurveyTitleList(): List<Result<SurveyPreview>> {
        return surveyFileDataSource.getSurveyTitleList()
    }

    override fun getSurveyList(): List<Survey> {
        return surveyFileDataSource.getSurveyList()
    }

    override fun exportSurveyResult(result: SurveyResult) {
        TODO("Not yet implemented")
    }

}