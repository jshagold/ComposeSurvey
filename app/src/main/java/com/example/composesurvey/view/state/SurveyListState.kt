package com.example.composesurvey.view.state

import com.example.composesurvey.view.error.ErrorCode

data class SurveyListState(
    val titleList: List<String> = listOf(),
    val errorCode: ErrorCode = ErrorCode.NONE
)