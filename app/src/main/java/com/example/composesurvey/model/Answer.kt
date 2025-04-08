package com.example.composesurvey.model

sealed class Answer {
    data class Text(val value: String): Answer()
    data class SingleChoice(val selected: String): Answer()
    data class MultipleChoice(val selected: List<String>): Answer()
    data class Slider(val value: Int): Answer()
    data class LikertScale(val selected: Int): Answer()
}