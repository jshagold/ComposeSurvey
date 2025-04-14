package com.example.composesurvey.data

import com.example.composesurvey.model.Survey

interface SurveyFileDataSource {
    
    fun getSurvey(fileName: String): Survey?

    fun getSurveyTitleList(): List<String>

    fun getSurveyList(): List<Survey>
}