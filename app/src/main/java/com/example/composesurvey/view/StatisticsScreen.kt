package com.example.composesurvey.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.composesurvey.model.QuestionAndAnswerUI
import com.example.composesurvey.viewmodel.SurveyStatisticsViewModel


@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun PreviewStatisticsScreen() {
    StatisticsScreen(
        listOf()
    )
}


@Composable
fun StatisticsRoute(
    viewModel: SurveyStatisticsViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    StatisticsScreen(
        uiState
    )
}


@Composable
fun StatisticsScreen(
    qnaList: List<QuestionAndAnswerUI>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        qnaList.forEach {
            Text(
                text = it.toString()
            )
        }
    }
}