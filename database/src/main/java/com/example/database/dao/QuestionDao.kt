package com.example.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.database.model.QuestionEntity
import com.example.database.model.QuestionWithAnswers

@Dao
interface QuestionDao {

    @Upsert
    suspend fun upsertQuestion(question: QuestionEntity)

    @Upsert
    suspend fun upsertQuestions(questionList: List<QuestionEntity>)

    @Delete
    suspend fun deleteQuestion(question: QuestionEntity)

    @Query("DELETE FROM question WHERE surveyId = :surveyId")
    suspend fun deleteQuestionsBySurveyId(surveyId: Long)

    @Query("SELECT * FROM question WHERE id = :questionId")
    suspend fun getQuestionById(questionId: Long): QuestionEntity?

    @Query("SELECT * FROM question WHERE surveyId = :surveyId")
    suspend fun getQuestionListBySurveyId(surveyId: Long): List<QuestionEntity>

    @Query("SELECT id FROM question WHERE surveyId = :surveyId")
    suspend fun getQuestionIdListBySurveyId(surveyId: Long): List<Long>

    @Transaction
    @Query("SELECT * FROM question WHERE surveyId = :surveyId")
    suspend fun getQuestionWithAnswerList(surveyId: Long): List<QuestionWithAnswers>
}