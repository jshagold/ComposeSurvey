package com.example.composesurvey.view.components.chart

import android.R.attr.x
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.composesurvey.view.theme.GraphColors
import com.example.composesurvey.view.theme.GraphColorsType2


@Preview
@Composable
fun PreviewStackBarChart() {
    StackBarChart(
        chartHeight = 75.dp,
        dataList = listOf(
            Pair("Clean Architecture", 23),
            Pair("MVVM", 15),
            Pair("MVI", 9),
            Pair("MVC", 4),
        ),
        colorList = GraphColorsType2
    )
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StackBarChart(
    modifier: Modifier = Modifier,
    // name, count pair sorted by count
    dataList: List<Pair<String, Int>> = listOf(),
    chartHeight: Dp,
    colorList: List<Color> = GraphColors
) {
    val countSum = dataList.sumOf { it.second }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(chartHeight)
                .background(Color.Transparent, RoundedCornerShape(10.dp))
        ) {

            var startXPoint = 0f
            dataList.forEachIndexed { index, data ->
                if(index == 0) {
                    drawPath(
                        path = roundRectPath(
                            topLeft = Offset(
                                x = startXPoint,
                                y = 0f
                            ),
                            size = Size(
                                width = size.width * data.second / countSum,
                                height = size.height
                            ),
                            topStartRadius = 10f,
                            bottomStartRadius = 10f
                        ),
                        color = colorList[0]
                    )
                } else if(index == dataList.lastIndex) {
                    drawPath(
                        path = roundRectPath(
                            topLeft = Offset(
                                x = startXPoint,
                                y = 0f
                            ),
                            size = Size(
                                width = size.width * data.second / countSum,
                                height = size.height
                            ),
                            topEndRadius = 10f,
                            bottomEndRadius = 10f
                        ),
                        color = colorList[index % colorList.size]
                    )
                } else {
                    drawRect(
                        color = colorList[index % colorList.size],
                        topLeft = Offset(
                            x = startXPoint,
                            y = 0f
                        ),
                        size = Size(
                            width = size.width * data.second / countSum,
                            height = size.height
                        )
                    )
                }

                startXPoint += size.width * data.second / countSum
            }

        }

        Spacer(
            modifier = Modifier
                .size(10.dp)
        )

        FlowRow(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            dataList.forEachIndexed { index, data ->
                LabelText(
                    labelText = data.first,
                    labelColor = colorList[index % colorList.size],
                    labelPadding = 10
                )
            }
        }

    }


}