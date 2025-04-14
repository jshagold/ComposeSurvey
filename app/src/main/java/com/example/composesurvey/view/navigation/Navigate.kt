package com.example.composesurvey.view.navigation

import androidx.navigation.NavController


fun NavController.navigateToSurveyList() {
    this.navigate(Route.LIST)
}


fun NavController.navigateToSurveyCheck() {
    this.navigate(Route.CHECK)
}