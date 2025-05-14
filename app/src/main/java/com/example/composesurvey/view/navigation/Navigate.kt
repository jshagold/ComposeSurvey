package com.example.composesurvey.view.navigation

import android.util.Log
import androidx.navigation.NavController
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


fun NavController.navigateToSurveyList() {
    this.navigate(Route.LIST) {
        popUpTo(Route.LIST) {
            this.inclusive = false
        }
        launchSingleTop = true
    }
}


fun NavController.navigateToSurveyCheck(surveyId: Long) {
    this.navigate("${Route.CHECK}/$surveyId")
}

fun NavController.navigateToSurveyResultList() {
    this.navigate(Route.STATISTICS_LIST)
}

fun NavController.navigateToSurveyStatistics(surveyId: Long) {
    this.navigate("${Route.STATISTICS}/$surveyId")
}