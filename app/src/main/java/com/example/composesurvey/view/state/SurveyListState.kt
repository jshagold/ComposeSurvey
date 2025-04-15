package com.example.composesurvey.view.state

import com.example.composesurvey.common.result.Result
import com.example.composesurvey.model.SurveyPreview
import com.example.composesurvey.view.error.ErrorCode

data class SurveyListState(
    val titleList: List<Result<SurveyPreview>> = listOf(),
    val errorCode: ErrorCode = ErrorCode.NONE
)