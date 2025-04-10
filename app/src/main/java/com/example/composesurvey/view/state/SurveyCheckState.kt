package com.example.composesurvey.view.state

import com.example.composesurvey.model.Answer
import com.example.composesurvey.model.Question

data class SurveyCheckState(
    val surveyTitle: String = "",
    val questionNAnswerList: List<Pair<Question, Answer>> = listOf(),
)