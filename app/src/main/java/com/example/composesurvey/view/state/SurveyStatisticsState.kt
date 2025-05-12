package com.example.composesurvey.view.state

import com.example.composesurvey.model.QuestionWithAnswerUI

data class SurveyStatisticsState(
    var questionWithAnswerList: List<QuestionWithAnswerUI> = listOf()
)