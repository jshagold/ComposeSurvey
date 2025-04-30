package com.example.composesurvey.view.navigation

import androidx.navigation.NavController
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


fun NavController.navigateToSurveyList() {
    this.navigate(Route.LIST)
}


fun NavController.navigateToSurveyCheck(surveyId: Long) {
    this.navigate("${Route.CHECK}/$surveyId")
}