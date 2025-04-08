package com.example.composesurvey.view.state

import com.example.composesurvey.model.Answer
import com.example.composesurvey.model.Question

data class SurveyCheckState(
    val questionNAnswer: List<Pair<Question, Answer>> = listOf()
)