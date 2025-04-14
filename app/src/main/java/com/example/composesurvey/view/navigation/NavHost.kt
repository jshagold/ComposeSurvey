package com.example.composesurvey.view.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composesurvey.view.MainRoute
import com.example.composesurvey.view.SurveyCheckRoute
import com.example.composesurvey.view.SurveyListRoute


@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Route.MAIN,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {

        composable(route = Route.MAIN) {
            MainRoute(
                navigateToSurveyList = {
                    navController.navigateToSurveyList()
                }
            )
        }

        composable(route = Route.LIST) {
            SurveyListRoute()
        }

        composable(route = Route.CHECK) {
            SurveyCheckRoute()
        }

        composable(route = Route.STATISTICS) {

        }

    }
}