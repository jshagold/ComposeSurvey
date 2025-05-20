package com.example.data.di

import com.example.data.datasource.SurveyDBDataSource
import com.example.data.datasource.SurveyDBDataSourceImpl
import com.example.data.datasource.SurveyFileDataSource
import com.example.data.datasource.SurveyFileDataSourceImpl
import com.example.data.repository.SurveyRepositoryImpl
import com.example.domain.repository.SurveyRepository
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
    fun bindSurveyDBDataSource(surveyDBDataSourceImpl: SurveyDBDataSourceImpl): SurveyDBDataSource

    @Binds
    @Singleton
    fun bindSurveyRepository(surveyRepositoryImpl: SurveyRepositoryImpl): SurveyRepository

}