package com.example.core.constants

object SurveyJson {
    const val REQUIRED = "required"
    const val OPTIONS = "options"
    const val MIN = "min"
    const val MAX = "max"
    const val SCALE_LIST = "scaleList"
    const val TYPE = "type"
    const val VALUE = "value"
    const val SELECTED = "selected"
    object Type {
        const val TEXT = "TEXT"
        const val SINGLE_CHOICE = "SINGLE_CHOICE"
        const val MULTIPLE_CHOICE = "MULTIPLE_CHOICE"
        const val SLIDER = "SLIDER"
        const val LIKERT_SCALE = "LIKERT_SCALE"
    }
}