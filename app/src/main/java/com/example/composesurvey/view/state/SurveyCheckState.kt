package com.example.composesurvey.view.state

import com.example.composesurvey.error.ErrorCode
import com.example.composesurvey.model.QuestionWithAnswerUI

data class SurveyCheckState(
    val surveyTitle: String = "",
    val questionNAnswerList: List<QuestionWithAnswerUI> = listOf(),
    val errorCode: ErrorCode = ErrorCode.NONE,
    val saveCompleteDialog: Boolean = false
)