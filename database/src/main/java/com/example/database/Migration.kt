package com.example.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1,2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("""
            CREATE TABLE answer_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                surveyId INTEGER NOT NULL,
                questionId INTEGER NOT NULL,
                answerGroupId TEXT NOT NULL,
                answerValue TEXT NOT NULL,
                answeredAt INTEGER NOT NULL,
                FOREIGN KEY(surveyId) REFERENCES survey(id) ON DELETE CASCADE,
                FOREIGN KEY(questionId) REFERENCES question(id) ON DELETE CASCADE
            )
        """)

        database.execSQL("""
            INSERT INTO answer_new (id, surveyId, questionId, answerGroupId, answerValue, answeredAt)
            SELECT id, surveyId, questionId, answerGroupId, answerValue, answeredAt FROM answer
        """)

        database.execSQL("DROP TABLE answer")
        database.execSQL("ALTER TABLE answer_new RENAME TO answer")
    }
}