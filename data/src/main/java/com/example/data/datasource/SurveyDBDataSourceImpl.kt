package com.example.data.datasource

import com.example.data.mapper.toData
import com.example.data.model.Answer
import com.example.data.model.Question
import com.example.data.model.Survey
import com.example.database.dao.AnswerDao
import com.example.database.dao.QuestionDao
import com.example.database.dao.SurveyDao
import javax.inject.Inject

class SurveyDBDataSourceImpl @Inject constructor(
    private val surveyDao: SurveyDao,
    private val questionDao: QuestionDao,
    private val answerDao: AnswerDao,
): SurveyDBDataSource {
    override fun getSurveyByFileName(fileName: String): Survey {
        TODO("Not yet implemented")
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

    override fun saveSurvey(survey: Survey) {
        TODO("Not yet implemented")
    }

    override fun saveQuestion(question: Question) {
        TODO("Not yet implemented")
    }

    override fun saveAnswer(answer: Answer) {
        TODO("Not yet implemented")
    }
}