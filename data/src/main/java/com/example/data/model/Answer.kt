package com.example.data.model

import com.example.core.constants.SurveyJson
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class Answer {
    @Serializable
    @SerialName(SurveyJson.Type.TEXT)
    data class Text(val value: String) : Answer()

    @Serializable
    @SerialName(SurveyJson.Type.SINGLE_CHOICE)
    data class SingleChoice(val selected: String) : Answer()

    @Serializable
    @SerialName(SurveyJson.Type.MULTIPLE_CHOICE)
    data class MultipleChoice(val selected: List<String>) : Answer()

    @Serializable
    @SerialName(SurveyJson.Type.SLIDER)
    data class Slider(val value: Int) : Answer()

    @Serializable
    @SerialName(SurveyJson.Type.LIKERT_SCALE)
    data class LikertScale(val selected: Int) : Answer()
}