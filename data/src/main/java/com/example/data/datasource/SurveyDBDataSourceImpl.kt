package com.example.data.datasource

import androidx.room.withTransaction
import com.example.core.constants.SurveyJson
import com.example.data.mapper.toData
import com.example.data.mapper.toEntity
import com.example.data.model.Answer
import com.example.data.model.Question
import com.example.data.model.QuestionWithAnswer
import com.example.data.model.Survey
import com.example.database.SurveyDatabase
import com.example.database.dao.AnswerDao
import com.example.database.dao.QuestionDao
import com.example.database.dao.SurveyDao
import com.example.database.model.AnswerEntity
import io.github.aakira.napier.Napier
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import java.util.UUID
import javax.inject.Inject

class SurveyDBDataSourceImpl @Inject constructor(
    private val surveyDB: SurveyDatabase,
    private val surveyDao: SurveyDao,
    private val questionDao: QuestionDao,
    private val answerDao: AnswerDao,
): SurveyDBDataSource {

    override suspend fun getSurvey(surveyId: Long): Survey? {
        val surveyWithQuestion = surveyDao.getSurveyWithQuestions(surveyId) ?: return null

        return Survey(
            id = surveyWithQuestion.survey.id,
            title = surveyWithQuestion.survey.title,
            description = surveyWithQuestion.survey.description,
            questions = surveyWithQuestion.questions.map { it.toData() }
        )
    }

    override suspend fun getSurveyList(): List<Survey> {
        return surveyDao.getAllSurveyWithQuestions().map { surveyWithQuestionEntity ->
            Survey(
                id = surveyWithQuestionEntity.survey.id,
                title = surveyWithQuestionEntity.survey.title,
                description = surveyWithQuestionEntity.survey.description,
                questions = surveyWithQuestionEntity.questions.map { it.toData() }
            )
        }
    }

    override suspend fun isExistSurvey(title: String): Boolean {
        return surveyDao.isExistSurvey(title)
    }

    override suspend fun saveSurveyWithQuestions(
        survey: Survey
    ) {
        surveyDB.withTransaction {
            val surveyId = surveyDao.upsertSurvey(survey.toEntity())
            val questionEntityList = survey.questions.map { it.toEntity(surveyId) }

            questionDao.upsertQuestions(questionEntityList)
        }
    }

    override suspend fun saveSurveyWithQuestionsIfNew(
        survey: Survey
    ) {
        surveyDB.withTransaction {
            if(!surveyDao.isExistSurvey(survey.title)) {
                val surveyId = surveyDao.upsertSurvey(survey.toEntity())
                val questionEntityList = survey.questions.map { it.toEntity(surveyId) }

                questionDao.upsertQuestions(questionEntityList)
            }
        }
    }

    override fun saveSurvey(survey: Survey) {
        TODO("Not yet implemented")
    }

    override fun saveQuestion(question: Question) {
        TODO("Not yet implemented")
    }

    override suspend fun saveAnswerList(answer: List<QuestionWithAnswer>) {
        val uuid = UUID.randomUUID().toString()
        val answerList = answer.map {
            AnswerEntity(
                surveyId = it.question.surveyId,
                questionId = it.question.id,
                answerGroupId = uuid,
                answerValue = Json.encodeToString(it.answer),
            )
        }.toTypedArray()

        answerDao.upsetAnswerList(*answerList)
    }

    override suspend fun getQuestionIdListBySurveyId(surveyId: Long): List<Long> {
        return questionDao.getQuestionIdListBySurveyId(surveyId)
    }

    override suspend fun getQuestionListBySurveyId(surveyId: Long): List<Question> {
        return questionDao.getQuestionListBySurveyId(surveyId).map { it.toData() }
    }

    override suspend fun getQuestionWithAnswerListBySurveyId(surveyId: Long): List<QuestionWithAnswer> {
        return questionDao.getQuestionWithAnswerList(surveyId).flatMap { questionWithAnswerList ->
            questionWithAnswerList.answers.map { answerEntity ->
                QuestionWithAnswer(
                    question = questionWithAnswerList.question.toData(),
                    answer = answerEntity.toData()
                )
            }
        }
    }

    override suspend fun getAnswerListBySurveyNQuestionId(
        surveyId: Long,
        questionId: Long
    ): List<Answer> {
        return answerDao.getAnswerListBySurveyNQuestionId(surveyId, questionId).map { answerEntity ->
            val jsonElement = Json.parseToJsonElement(answerEntity.answerValue).jsonObject[SurveyJson.TYPE]
            val type = Json.decodeFromJsonElement<String>(jsonElement!!)
            return@map when(type) {
                SurveyJson.Type.TEXT -> {
                    runCatching {
                        val jsonAnswerValue = Json.parseToJsonElement(answerEntity.answerValue).jsonObject[SurveyJson.VALUE]
                        val answerValue = Json.decodeFromJsonElement<String>(jsonAnswerValue!!)
                        Answer.Text(value = answerValue)
                    }.onFailure {
                        Napier.e(message = "data = $answerEntity", throwable = it)
                    }.getOrNull()
                }

                SurveyJson.Type.SINGLE_CHOICE -> {
                    runCatching {
                        val jsonAnswerValue = Json.parseToJsonElement(answerEntity.answerValue).jsonObject[SurveyJson.SELECTED]
                        val answerValue = Json.decodeFromJsonElement<String>(jsonAnswerValue!!)
                        Answer.SingleChoice(selected = answerValue)
                    }.onFailure {
                        Napier.e(message = "data = $answerEntity", throwable = it)
                    }.getOrNull()
                }

                SurveyJson.Type.MULTIPLE_CHOICE -> {
                    runCatching {
                        val jsonAnswerValue = Json.parseToJsonElement(answerEntity.answerValue).jsonObject[SurveyJson.SELECTED]
                        val answerValue = Json.decodeFromJsonElement<List<String>>(jsonAnswerValue!!)
                        Answer.MultipleChoice(selected = answerValue)
                    }.onFailure {
                        Napier.e(message = "data = $answerEntity", throwable = it)
                    }.getOrNull()
                }

                SurveyJson.Type.SLIDER -> {
                    runCatching {
                        val jsonAnswerValue = Json.parseToJsonElement(answerEntity.answerValue).jsonObject[SurveyJson.VALUE]
                        val answerValue = Json.decodeFromJsonElement<Int>(jsonAnswerValue!!)
                        Answer.Slider(value = answerValue)
                    }.onFailure {
                        Napier.e(message = "data = $answerEntity", throwable = it)
                    }.getOrNull()
                }

                SurveyJson.Type.LIKERT_SCALE -> {
                    runCatching {
                        val jsonAnswerValue = Json.parseToJsonElement(answerEntity.answerValue).jsonObject[SurveyJson.SELECTED]
                        val answerValue = Json.decodeFromJsonElement<Int>(jsonAnswerValue!!)
                        Answer.LikertScale(selected = answerValue)
                    }.onFailure {
                        Napier.e(message = "data = $answerEntity", throwable = it)
                    }.getOrNull()
                }

                else -> {
                    null
                }
            }
        }.filterNotNull()
    }
}