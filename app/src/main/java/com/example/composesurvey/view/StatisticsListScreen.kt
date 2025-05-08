package com.example.composesurvey.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.composesurvey.view.components.extension.noRippleClickable
import com.example.composesurvey.view.state.SurveyResultListState
import com.example.composesurvey.viewmodel.SurveyStatisticsListViewModel
import com.example.core.result.Result


@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun PreviewStatisticsListScreen() {
    StatisticsListScreen(
        uiState = SurveyResultListState()
    )
}

@Composable
fun StatisticsListRoute(
    modifier: Modifier = Modifier,
    viewModel: SurveyStatisticsListViewModel = hiltViewModel(),
    navigateToSurveyStatistics: (surveyId: Long) -> Unit = {},
) {

    val uiState by viewModel.surveyResultListState.collectAsStateWithLifecycle()

    StatisticsListScreen(
        modifier = modifier,
        uiState = uiState,
        navigateToSurveyStatistics = navigateToSurveyStatistics
    )
}



@Composable
fun StatisticsListScreen(
    modifier: Modifier = Modifier,
    uiState: SurveyResultListState,
    navigateToSurveyStatistics: (surveyId: Long) -> Unit = {},
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        modifier = modifier
            .fillMaxSize()
            .background(White)
            .padding(horizontal = 10.dp)
    ) {
        item {
            Text(
                text = "Survey List"
            )
        }

        items(uiState.surveyList) { resultTitle ->
            when(resultTitle) {
                is Result.Success -> {
                    Text(
                        text = resultTitle.data.title,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, Black, RoundedCornerShape(10.dp))
                            .noRippleClickable {
                                navigateToSurveyStatistics(resultTitle.data.id)
                            }
                            .padding(10.dp)

                    )
                }
                is Result.Failure -> {
                    Text(
                        text = "파일 에러 ${resultTitle.message}",
                        color = White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, Black, RoundedCornerShape(10.dp))
                            .background(Gray, RoundedCornerShape(10.dp))
                            .padding(10.dp)
                    )
                }
            }
        }
    }
}