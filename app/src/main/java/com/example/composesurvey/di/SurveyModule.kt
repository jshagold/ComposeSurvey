package com.example.composesurvey.di

import com.example.composesurvey.data.SurveyFileDataSource
import com.example.composesurvey.data.SurveyFileDataSourceImpl
import com.example.composesurvey.data.SurveyRepository
import com.example.composesurvey.data.SurveyRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SurveyModule {

    @Binds
    @Singleton
    fun bindSurveyFileDataSource(surveyFileDataSourceImpl: SurveyFileDataSourceImpl): SurveyFileDataSource

    @Binds
    @Singleton
    fun bindSurveyRepository(surveyRepositoryImpl: SurveyRepositoryImpl): SurveyRepository

}