package com.example.composesurvey.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composesurvey.viewmodel.MainViewModel


@Composable
fun MainRoute(
    modifier: Modifier = Modifier,
    navigateToSurveyList: () -> Unit = {},
    navigateToSurveyResultList: () -> Unit = {},
    viewModel: MainViewModel = hiltViewModel()
) {


    MainScreen(
        navigateToSurveyList = navigateToSurveyList,
        navigateToSurveyResultList = navigateToSurveyResultList,
        updateSurveyFromFile = viewModel::updateSurvey
    )
}


@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navigateToSurveyList: () -> Unit = {},
    navigateToSurveyResultList: () -> Unit = {},
    updateSurveyFromFile: () -> Unit = {},
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
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

        Button(
            onClick = navigateToSurveyResultList,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "ToSurveyResult"
            )
        }

        Button(
            onClick = updateSurveyFromFile,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Update Survey From File"
            )
        }
    }
}