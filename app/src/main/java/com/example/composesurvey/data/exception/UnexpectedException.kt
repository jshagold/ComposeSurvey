package com.example.composesurvey.data.exception

import com.example.composesurvey.exception.BaseException

class UnexpectedException(msg: String, cause: Throwable? = null) : BaseException(msg, cause)