package com.example.composesurvey.data

import com.example.composesurvey.model.Survey

interface SurveyFileDataSource {
    fun loadSurveyList(): List<Survey>
}