package com.example.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class Answer {
    @Serializable
    @SerialName("Text")
    data class Text(val value: String) : Answer()

    @Serializable
    @SerialName("SingleChoice")
    data class SingleChoice(val selected: String) : Answer()

    @Serializable
    @SerialName("MultipleChoice")
    data class MultipleChoice(val selected: List<String>) : Answer()

    @Serializable
    @SerialName("Slider")
    data class Slider(val value: Int) : Answer()

    @Serializable
    @SerialName("LikertScale")
    data class LikertScale(val selected: Int) : Answer()
}