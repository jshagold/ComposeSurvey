package com.example.composesurvey.view.model

sealed class AnswerUI {
    data class Text(val value: String) : AnswerUI()

    data class SingleChoice(val selected: String) : AnswerUI()

    data class MultipleChoice(val selected: List<String>) : AnswerUI()

    data class Slider(val value: Int) : AnswerUI()

    data class LikertScale(val selected: Int) : AnswerUI()
}