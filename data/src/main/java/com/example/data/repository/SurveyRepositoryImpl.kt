package com.example.data.repository

import com.example.core.result.Result
import com.example.data.datasource.SurveyFileDataSource
import com.example.domain.model.Survey
import com.example.domain.model.SurveyPreview
import com.example.domain.model.SurveyResult
import com.example.domain.repository.SurveyRepository
import javax.inject.Inject

class SurveyRepositoryImpl @Inject constructor(
    val surveyFileDataSource: SurveyFileDataSource
) : SurveyRepository {
    override fun getSurvey(surveyId: Long): Survey {
        TODO("Not yet implemented")
    }

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