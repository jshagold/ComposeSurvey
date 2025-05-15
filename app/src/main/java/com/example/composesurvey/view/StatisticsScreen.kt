package com.example.composesurvey.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.composesurvey.model.QuestionTypeUI
import com.example.composesurvey.view.components.chart.pie.PieChart
import com.example.composesurvey.view.components.chart.StackBarChart
import com.example.composesurvey.view.components.chart.divergingstackedbar.DivergingStackedBarChart
import com.example.composesurvey.view.components.chart.histogram.Histogram
import com.example.composesurvey.view.state.SurveyStatisticsState
import com.example.composesurvey.view.theme.GraphColors
import com.example.composesurvey.view.theme.GraphColorsType2
import com.example.composesurvey.viewmodel.SurveyStatisticsViewModel


@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun PreviewStatisticsScreen() {
    StatisticsScreen(
        SurveyStatisticsState()
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
    uiState: SurveyStatisticsState,
) {
    val scrollState = rememberScrollState()




    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp)
    ) {

        uiState.statisticsList.forEach { (question, map) ->
            when(question.type) {
                QuestionTypeUI.TEXT -> {
                    Spacer(modifier = Modifier.size(50.dp))
                    Text(
                        text = "${question.id} ${question.question}"
                    )
                }

                QuestionTypeUI.SINGLE_CHOICE -> {
                    // Single Choice - 막대그래프 or pie chart

                    var pieSelectedIndex: MutableState<Int?> = remember { mutableStateOf(null) }

                    Spacer(modifier = Modifier.size(50.dp))
                    Text(
                        text = "${question.id} ${question.question}"
                    )

                    PieChart(
                        dataList = map.map { (k, v) -> Pair(k, v) },
                        colorList = GraphColorsType2,
                        selectedDataIndex = pieSelectedIndex.value,
                        onClickPie = {
                            pieSelectedIndex.value = it
                        },
                        modifier = Modifier
                    )
                }

                QuestionTypeUI.MULTIPLE_CHOICE -> {
                    // multiple choice - 수평 누적 막대그래프 or 막대그래프
                    Spacer(modifier = Modifier.size(50.dp))
                    Text(
                        text = "${question.id} ${question.question}"
                    )

                    StackBarChart(
                        dataList = map.map { (k, v) -> Pair(k, v) }.sortedByDescending { (k, v) -> v },
                        chartHeight = 100.dp,
                        colorList = GraphColorsType2,
                        modifier = Modifier
                    )
                }

                QuestionTypeUI.SLIDER -> {
                    // Slider - 히스토그램
                    Spacer(modifier = Modifier.size(50.dp))
                    Text(
                        text = "${question.id} ${question.question}"
                    )

                    Histogram(
                        modifier = Modifier,
                        candleColor = GraphColorsType2[0],
                        dataList = map,
                        min = question.min!!.toInt(),
                        max = question.max!!.toInt(),
                    )
                }

                QuestionTypeUI.LIKERT_SCALE -> {
                    // Likert Scale - 수평 막대그래프, 누적 막대그래프
                    Spacer(modifier = Modifier.size(50.dp))
                    Text(
                        text = "${question.id} ${question.question}"
                    )

                    DivergingStackedBarChart(
                        modifier = Modifier,
                        dataList = map.toSortedMap().map { (k, v) -> Pair(question.scaleList!![k.toInt()], v) }
                    )
                }
            }
        }

    }
}