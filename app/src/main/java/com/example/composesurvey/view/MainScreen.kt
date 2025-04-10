package com.example.composesurvey.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun MainRoute(
    modifier: Modifier = Modifier,
    navigateToSurveyCheck: () -> Unit = {}
) {


    MainScreen(
        navigateToSurveyCheck = navigateToSurveyCheck
    )
}


@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navigateToSurveyCheck: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Button(
            onClick = navigateToSurveyCheck
        ) {
            Text(
                text = "toCheckScreen"
            )
        }
    }
}