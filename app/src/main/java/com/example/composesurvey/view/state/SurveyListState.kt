package com.example.composesurvey.view.state

import com.example.composesurvey.view.error.ErrorCode
import com.example.composesurvey.model.SurveyPreviewUI
import com.example.core.result.Result

data class SurveyListState(
    val titleList: List<Result<SurveyPreviewUI>> = listOf(),
    val errorCode: ErrorCode = ErrorCode.NONE
)