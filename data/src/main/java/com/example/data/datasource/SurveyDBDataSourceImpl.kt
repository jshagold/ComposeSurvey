package com.example.data.datasource

import androidx.room.withTransaction
import com.example.data.mapper.toData
import com.example.data.mapper.toEntity
import com.example.data.model.Question
import com.example.data.model.QuestionAndAnswer
import com.example.data.model.Survey
import com.example.database.SurveyDatabase
import com.example.database.dao.AnswerDao
import com.example.database.dao.QuestionDao
import com.example.database.dao.SurveyDao
import com.example.database.model.AnswerEntity
import kotlinx.serialization.json.Json
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

    override suspend fun saveAnswerList(answer: List<QuestionAndAnswer>) {
        val answerList = answer.map {
            AnswerEntity(
                surveyId = it.question.surveyId,
                questionId = it.question.id,
                answerGroupId = UUID.randomUUID().toString(),
                answerValue = Json.encodeToString(it.answer),
            )
        }.toTypedArray()

        answerDao.upsetAnswerList(*answerList)
    }
}