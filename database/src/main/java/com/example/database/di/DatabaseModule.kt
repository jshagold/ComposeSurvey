package com.example.database.di

import android.content.Context
import androidx.room.Room
import com.example.database.MIGRATION_1_2
import com.example.database.SurveyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesSurveyDatabase(
        @ApplicationContext context: Context
    ): SurveyDatabase = Room.databaseBuilder(
        context = context,
        klass = SurveyDatabase::class.java,
        name = "survey-database"
    )
        .addMigrations(MIGRATION_1_2)
        .build()

}