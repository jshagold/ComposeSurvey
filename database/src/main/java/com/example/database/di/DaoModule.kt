package com.example.database.di

import com.example.database.SurveyDatabase
import com.example.database.dao.AnswerDao
import com.example.database.dao.QuestionDao
import com.example.database.dao.SurveyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    fun providesSurveyDao(
        database: SurveyDatabase
    ): SurveyDao = database.surveyDao()

    @Provides
    fun providesQuestionDao(
        database: SurveyDatabase
    ): QuestionDao = database.questionDao()

    @Provides
    fun providesAnswerDao(
        database: SurveyDatabase
    ): AnswerDao = database.answerDao()

}