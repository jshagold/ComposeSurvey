package com.example.data.mapper

import com.example.core.serialize.parseMaxIfNeeded
import com.example.core.serialize.parseMinIfNeeded
import com.example.core.serialize.parseOptionsIfNeeded
import com.example.core.serialize.parseRequired
import com.example.core.serialize.parseScaleListIfNeeded
import com.example.data.model.Question
import com.example.data.model.QuestionOptionJsonDTO
import com.example.data.model.QuestionType
import com.example.database.model.QuestionEntity
import kotlinx.serialization.json.Json
import com.example.domain.model.Question as QuestionDomain

fun Question.toDomain() = QuestionDomain(
    id = this.id,
    type = this.type.toDomain(),
    question = this.question,
    required = this.required,
    options = this.options,
    min = this.min,
    max = this.max,
    scaleList = this.scaleList
)

fun QuestionDomain.toData() = Question(
    id = this.id,
    type = this.type.toData(),
    question = this.question,
    required = this.required,
    options = this.options,
    min = this.min,
    max = this.max,
    scaleList = this.scaleList
)

// todo json key값 하드코딩
fun Question.toEntity(surveyId: Long): QuestionEntity {
    val jsonOption = Json.encodeToString(
        QuestionOptionJsonDTO(
            required = this.required,
            options = this.options,
            min = this.min,
            max = this.max,
            scaleList = this.scaleList,
        )
    )

    return QuestionEntity(
        surveyId = surveyId,
        questionText = this.question,
        type = this.type.name,
        jsonOption = jsonOption,
    )
}


fun QuestionEntity.toData(): Question {
    return Question(
        id = this.id.toString(),
        type = QuestionType.valueOf(this.type),
        question = this.questionText,
        required = parseRequired(this.jsonOption),
        options = parseOptionsIfNeeded(this.type, this.jsonOption),
        min = parseMinIfNeeded(this.type, this.jsonOption),
        max = parseMaxIfNeeded(this.type, this.jsonOption),
        scaleList = parseScaleListIfNeeded(this.type, this.jsonOption),
    )
}