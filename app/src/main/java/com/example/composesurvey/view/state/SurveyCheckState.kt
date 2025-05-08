package com.example.composesurvey.view.state

import com.example.composesurvey.view.error.ErrorCode
import com.example.composesurvey.model.QuestionAndAnswerUI

data class SurveyCheckState(
    val surveyTitle: String = "",
    val questionNAnswerList: List<QuestionAndAnswerUI> = listOf(),
    val errorCode: ErrorCode = ErrorCode.NONE,
    val saveCompleteDialog: Boolean = false
)