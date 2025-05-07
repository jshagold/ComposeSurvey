package com.example.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.database.model.AnswerEntity

@Dao
interface AnswerDao {

    @Upsert
    suspend fun upsertAnswer(answer: AnswerEntity)

    @Upsert
    suspend fun upsetAnswerList(vararg answer: AnswerEntity)

    @Query("SELECT * FROM answer WHERE answerGroupId = :groupId")
    suspend fun getAnswerByGroupId(groupId: String): List<AnswerEntity>
}