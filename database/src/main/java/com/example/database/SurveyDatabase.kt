package com.example.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.database.dao.AnswerDao
import com.example.database.dao.QuestionDao
import com.example.database.dao.SurveyDao
import com.example.database.model.AnswerEntity
import com.example.database.model.QuestionEntity
import com.example.database.model.SurveyEntity

@Database(
    entities = [
        SurveyEntity::class,
        QuestionEntity::class,
        AnswerEntity::class,
    ],
    version = 1
)
abstract class SurveyDatabase : RoomDatabase() {
    abstract fun surveyDao(): SurveyDao
    abstract fun questionDao(): QuestionDao
    abstract fun answerDao(): AnswerDao
}