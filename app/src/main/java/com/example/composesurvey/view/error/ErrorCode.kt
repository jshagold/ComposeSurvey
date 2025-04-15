package com.example.composesurvey.view.error

enum class ErrorCode(val errorName: String, val code: Int) {
    NONE(errorName = "", code = -1),
    UNEXPECTED(errorName = "예외 에러", code = 0),
    FILE(errorName = "파일 에러", code = 1),
    NULLELEMENT(errorName = "항목이 없습니다.", code = 2)
}