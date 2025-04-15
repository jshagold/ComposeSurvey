package com.example.composesurvey.view.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composesurvey.view.MainRoute
import com.example.composesurvey.view.SurveyCheckRoute
import com.example.composesurvey.view.SurveyListRoute
import com.example.composesurvey.viewmodel.SurveyViewModel


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
            SurveyListRoute(
                navigateToSurveyCheck = { fileName ->
                    navController.navigateToSurveyCheck(fileName = fileName)
                }
            )
        }

        composable(route = "${Route.CHECK}/{fileName}") { navBackStackEntry ->
            val surveyTitle = navBackStackEntry.arguments?.getString("fileName")
            val viewModel = navBackStackEntry.sharedViewModel<SurveyViewModel>(navController = navController)



            SurveyCheckRoute()
        }

        composable(route = Route.STATISTICS) {

        }

    }
}

/**
 * 같은 navBackStackEntry에서 공유할 수 있는 ViewModel return하는 inline 함수
 */
@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }

    return hiltViewModel(parentEntry)
}
