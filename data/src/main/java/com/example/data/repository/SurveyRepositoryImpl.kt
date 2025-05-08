package com.example.data.repository

import com.example.core.exception.FileException
import com.example.core.exception.UnexpectedException
import com.example.core.result.Result
import com.example.data.datasource.SurveyDBDataSource
import com.example.data.datasource.SurveyFileDataSource
import com.example.data.mapper.toData
import com.example.data.mapper.toDomain
import com.example.domain.model.QuestionAndAnswer
import com.example.domain.model.Survey
import com.example.domain.model.SurveyPreview
import com.example.domain.model.SurveyResult
import com.example.domain.repository.SurveyRepository
import com.example.data.model.Survey as SurveyData
import javax.inject.Inject

class SurveyRepositoryImpl @Inject constructor(
    val surveyFileDataSource: SurveyFileDataSource,
    val surveyDBDataSource: SurveyDBDataSource,
) : SurveyRepository {
    override suspend fun getSurvey(surveyId: Long): Survey? {
        return surveyDBDataSource.getSurvey(surveyId)?.toDomain()
    }

    override fun getSurvey(fileName: String): Survey {
        return surveyFileDataSource.getSurvey(fileName).toDomain()
    }

    override fun getSurveyTitleList(): List<Result<SurveyPreview>> {
        return surveyFileDataSource.getSurveyTitleList()
    }

    override suspend fun getSurveyList(): List<Result<Survey>> {
        return surveyDBDataSource.getSurveyList().map { survey: SurveyData ->
            Result.Success(survey.toDomain())
        }
    }

    override suspend fun saveSurveyToDBFromFile(): Result<String> {
        return try {
            val surveyList: List<SurveyData> = surveyFileDataSource.getSurveyList()
            surveyList.forEach {
                surveyDBDataSource.saveSurveyWithQuestionsIfNew(it)
            }

            Result.Success("success")
        } catch (e: FileException) {
            Result.Failure("saveSurveyToDBFromFile", e)
        } catch (e: UnexpectedException) {
            Result.Failure("saveSurveyToDBFromFile", e)
        } catch (e: Exception) {
            Result.Failure("saveSurveyToDBFromFile", UnexpectedException(msg = "예상 외", cause = e))
        }
    }

    override suspend fun saveSurveyResult(result: List<QuestionAndAnswer>) {
        val resultData = result.map { it.toData() }

        surveyDBDataSource.saveAnswerList(resultData)
    }


    override fun exportSurveyResult(result: SurveyResult) {
        TODO("Not yet implemented")
    }

    override suspend fun getQuestionAndAnswerListBySurveyId(surveyId: Long): List<QuestionAndAnswer> {
        return surveyDBDataSource.getQuestionAndAnswerListBySurveyId(surveyId).map { it.toDomain() }
    }

}