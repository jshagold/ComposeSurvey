package com.example.composesurvey.view.state

import com.example.composesurvey.model.SurveyUI
import com.example.composesurvey.error.ErrorCode
import com.example.core.result.Result

data class SurveyResultListState(
    val surveyList: List<Result<SurveyUI>> = listOf(),
    val errorCode: ErrorCode = ErrorCode.NONE
)