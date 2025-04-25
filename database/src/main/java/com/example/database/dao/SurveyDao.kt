package com.example.database.dao

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
    suspend fun upsertSurvey(surveyEntity: SurveyEntity)

    @Delete
    suspend fun deleteSurvey(surveyEntity: SurveyEntity)

    @Query("SELECT * FROM survey")
    suspend fun getAllSurveys() : List<SurveyEntity>

    @Query("SELECT * FROM survey WHERE id = :surveyId")
    suspend fun getSurveyById(surveyId: Long): SurveyEntity?


    @Transaction
    @Query("SELECT * FROM survey WHERE id = :surveyId ")
    suspend fun getSurveyWithQuestions(surveyId: Long): SurveyWithQuestions?

    @Transaction
    @Query("SELECT * FROM survey")
    suspend fun getAllSurveyWithQuestions(): List<SurveyWithQuestions>
}