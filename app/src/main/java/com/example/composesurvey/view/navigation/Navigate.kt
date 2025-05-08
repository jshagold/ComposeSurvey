package com.example.composesurvey.view.navigation

import androidx.navigation.NavController
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


fun NavController.navigateToSurveyList() {
    val navController = this
    navController.navigate(Route.LIST) {
//        popUpTo(navController.graph.id) {
//            inclusive = false
//        }
    }
}


fun NavController.navigateToSurveyCheck(surveyId: Long) {
    this.navigate("${Route.CHECK}/$surveyId")
}