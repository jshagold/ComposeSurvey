package com.example.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.example.database.model.SurveyEntity
import com.example.database.model.SurveyWithQuestions

@Dao
interface SurveyDao {

    @Upsert
    suspend fun upsertSurvey(surveyEntity: SurveyEntity): Long

    @Delete
    suspend fun deleteSurvey(surveyEntity: SurveyEntity)

    @Query("SELECT * FROM survey WHERE id = :surveyId")
    suspend fun getSurveyById(surveyId: Long): SurveyEntity?

    @Query("SELECT * FROM survey")
    suspend fun getAllSurveys() : List<SurveyEntity>

    @Query("SELECT * FROM survey LIMIT :limit OFFSET (:page-1) * :limit")
    suspend fun getSurveyListByPage(page: Int, limit: Int): List<SurveyEntity>

    @Query("SELECT * FROM survey")
    fun getSurveyListAsPageSource(): PagingSource<Int, SurveyEntity>

    @Query("SELECT EXISTS(SELECT * FROM survey WHERE title = :surveyTitle)")
    suspend fun isExistSurvey(surveyTitle: String): Boolean

    @Transaction
    @Query("SELECT * FROM survey")
    suspend fun getAllSurveyWithQuestions(): List<SurveyWithQuestions>

    @Transaction
    @Query("SELECT * FROM survey WHERE id = :surveyId")
    suspend fun getSurveyWithQuestions(surveyId: Long): SurveyWithQuestions?

    @Transaction
    @Query("SELECT * FROM survey LIMIT :limit OFFSET (:page-1) * :limit")
    suspend fun getSurveyWithQuestionsListByPage(page: Int, limit: Int): List<SurveyWithQuestions>

    @Transaction
    @Query("SELECT * FROM survey")
    fun getSurveyWithQuestionsListAsPageSource(): PagingSource<Int, SurveyWithQuestions>
    
}
