package com.example.composesurvey.view.state

import com.example.composesurvey.model.Answer
import com.example.composesurvey.model.Question
import com.example.composesurvey.view.error.ErrorCode

data class SurveyCheckState(
    val surveyTitle: String = "",
    val questionNAnswerList: List<Pair<Question, Answer>> = listOf(),
    val errorCode: ErrorCode = ErrorCode.NONE
)