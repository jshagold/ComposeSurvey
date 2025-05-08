package com.example.data.mapper

import com.example.core.constants.SurveyJson
import com.example.data.model.Answer
import com.example.database.model.AnswerEntity
import io.github.aakira.napier.Napier
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import com.example.domain.model.Answer as AnswerDomain

fun Answer.toDomain(): AnswerDomain {
    return when(this) {
        is Answer.LikertScale -> AnswerDomain.LikertScale(selected = this.selected)
        is Answer.MultipleChoice -> AnswerDomain.MultipleChoice(selected = this.selected)
        is Answer.SingleChoice -> AnswerDomain.SingleChoice(selected = this.selected)
        is Answer.Slider -> AnswerDomain.Slider(value = this.value)
        is Answer.Text -> AnswerDomain.Text(value = this.value)
    }
}

fun AnswerDomain.toData(): Answer {
    return when(this) {
        is AnswerDomain.LikertScale -> Answer.LikertScale(selected = this.selected)
        is AnswerDomain.MultipleChoice -> Answer.MultipleChoice(selected = this.selected)
        is AnswerDomain.SingleChoice -> Answer.SingleChoice(selected = this.selected)
        is AnswerDomain.Slider -> Answer.Slider(value = this.value)
        is AnswerDomain.Text -> Answer.Text(value = this.value)
    }
}


fun AnswerEntity.toData(): Answer {
    val jsonElement = Json.parseToJsonElement(this.answerValue).jsonObject[SurveyJson.TYPE]
    val type = Json.decodeFromJsonElement<String>(jsonElement!!)
    val value: Answer? = when(type) {
        SurveyJson.Type.TEXT -> {
            runCatching {
                val jsonAnswerValue = Json.parseToJsonElement(this.answerValue).jsonObject[SurveyJson.VALUE]
                val answerValue = Json.decodeFromJsonElement<String>(jsonAnswerValue!!)
                Answer.Text(value = answerValue)
            }.onFailure {
                Napier.e(message = "data = $this", throwable = it)
            }.getOrNull()
        }

        SurveyJson.Type.SINGLE_CHOICE -> {
            runCatching {
                val jsonAnswerValue = Json.parseToJsonElement(this.answerValue).jsonObject[SurveyJson.SELECTED]
                val answerValue = Json.decodeFromJsonElement<String>(jsonAnswerValue!!)
                Answer.SingleChoice(selected = answerValue)
            }.onFailure {
                Napier.e(message = "data = $this", throwable = it)
            }.getOrNull()
        }

        SurveyJson.Type.MULTIPLE_CHOICE -> {
            runCatching {
                val jsonAnswerValue = Json.parseToJsonElement(this.answerValue).jsonObject[SurveyJson.SELECTED]
                val answerValue = Json.decodeFromJsonElement<List<String>>(jsonAnswerValue!!)
                Answer.MultipleChoice(selected = answerValue)
            }.onFailure {
                Napier.e(message = "data = $this", throwable = it)
            }.getOrNull()
        }

        SurveyJson.Type.SLIDER -> {
            runCatching {
                val jsonAnswerValue = Json.parseToJsonElement(this.answerValue).jsonObject[SurveyJson.VALUE]
                val answerValue = Json.decodeFromJsonElement<Int>(jsonAnswerValue!!)
                Answer.Slider(value = answerValue)
            }.onFailure {
                Napier.e(message = "data = $this", throwable = it)
            }.getOrNull()
        }

        SurveyJson.Type.LIKERT_SCALE -> {
            runCatching {
                val jsonAnswerValue = Json.parseToJsonElement(this.answerValue).jsonObject[SurveyJson.SELECTED]
                val answerValue = Json.decodeFromJsonElement<Int>(jsonAnswerValue!!)
                Answer.LikertScale(selected = answerValue)
            }.onFailure {
                Napier.e(message = "data = $this", throwable = it)
            }.getOrNull()
        }

        else -> {
            null
        }
    }

    return value ?: throw NullPointerException("answer is null")
}