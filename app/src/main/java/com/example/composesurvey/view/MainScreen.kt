package com.example.composesurvey.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun MainRoute(
    modifier: Modifier = Modifier,
    navigateToSurveyList: () -> Unit = {}
) {


    MainScreen(
        navigateToSurveyList = navigateToSurveyList
    )
}


@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navigateToSurveyList: () -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Button(
            onClick = navigateToSurveyList,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "ToSurveyList"
            )
        }
    }
}