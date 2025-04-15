package com.example.composesurvey.view.navigation

import androidx.navigation.NavController
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


fun NavController.navigateToSurveyList() {
    this.navigate(Route.LIST)
}


fun NavController.navigateToSurveyCheck(fileName: String) {
    val encodeFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString())
    this.navigate("${Route.CHECK}/$encodeFileName")
}