package com.example.data.repository

import com.example.core.exception.FileException
import com.example.core.exception.UnexpectedException
import com.example.core.result.Result
import com.example.data.datasource.SurveyDBDataSource
import com.example.data.datasource.SurveyFileDataSource
import com.example.data.mapper.toData
import com.example.data.mapper.toDomain
import com.example.domain.model.Answer
import com.example.domain.model.Question
import com.example.domain.model.QuestionType
import com.example.domain.model.QuestionWithAnswer
import com.example.data.model.QuestionWithAnswer as QuestionWithAnswerData
import com.example.domain.model.Survey
import com.example.domain.model.SurveyPreview
import com.example.domain.model.SurveyResult
import com.example.domain.repository.SurveyRepository
import com.example.data.model.Survey as SurveyData
import javax.inject.Inject
import kotlin.collections.map

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

    override suspend fun saveSurveyResult(result: List<QuestionWithAnswer>) {
        val resultData = result.map { it.toData() }

        surveyDBDataSource.saveAnswerList(resultData)
    }


    override fun exportSurveyResult(result: SurveyResult) {
        TODO("Not yet implemented")
    }

    override suspend fun getStatisticsDataFromSurvey(surveyId: Long): LinkedHashMap<Question, Map<String, Int>> {
        val map: LinkedHashMap<Question, MutableMap<String, Int>> = LinkedHashMap<Question, MutableMap<String, Int>>()

        surveyDBDataSource.getQuestionWithAnswerListBySurveyId(surveyId).forEach { qWA ->
            val question = qWA.question.toDomain()
            val answer = qWA.answer.toDomain()

            val keys: List<String> = when(answer) {
                is Answer.LikertScale -> listOf(answer.selected.toString())
                is Answer.MultipleChoice -> answer.selected
                is Answer.SingleChoice -> listOf(answer.selected.toString())
                is Answer.Slider -> listOf(answer.value.toString())
                is Answer.Text -> listOf(answer.value.toString())
            }

            val answerMap = map.getOrPut(question) { mutableMapOf() }
            keys.forEach { key ->
                answerMap[key] = answerMap.getOrDefault(key, 0) + 1
            }
        }

        return map.mapValuesTo(LinkedHashMap()) { it.value.toMap() }
    }

    override suspend fun getQuestionWithAnswerListBySurveyId(surveyId: Long): List<QuestionWithAnswer> {
        return surveyDBDataSource.getQuestionWithAnswerListBySurveyId(surveyId).map { it.toDomain() }
    }

}