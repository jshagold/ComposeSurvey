package com.example.composesurvey.view.state

import com.example.composesurvey.model.QuestionUI
import com.example.composesurvey.model.QuestionWithAnswerUI

data class SurveyStatisticsState(
    var questionWithAnswerList: List<QuestionWithAnswerUI> = listOf(),
    val statisticsList: LinkedHashMap<QuestionUI, Map<String, Int>> = LinkedHashMap()
)