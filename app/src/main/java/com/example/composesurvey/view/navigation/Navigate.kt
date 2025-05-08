package com.example.composesurvey.view.navigation

import androidx.navigation.NavController
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


fun NavController.navigateToSurveyList(popUpBack: Boolean) {
    val navController = this
    navController.navigate(Route.LIST) {
        if(popUpBack) {
            popUpTo(navController.graph.id) {
                this.inclusive = false
            }
        }
    }
}


fun NavController.navigateToSurveyCheck(surveyId: Long) {
    this.navigate("${Route.CHECK}/$surveyId")
}

fun NavController.navigateToSurveyResultList() {
    this.navigate(Route.STATISTICS_LIST)
}