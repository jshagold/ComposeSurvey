package com.example.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.database.model.QuestionEntity

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
    suspend fun getQuestionsBySurveyId(surveyId: Long): List<QuestionEntity>
}