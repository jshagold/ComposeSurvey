package com.example.composesurvey.view.components.chart.divergingstackedbar

import android.R.attr.data
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composesurvey.view.components.chart.LabelText
import com.example.composesurvey.view.theme.DivergingStackedBarChartColors

@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun PreviewDivergingStackedBarChart() {
    DivergingStackedBarChart(
        dataList = listOf(
            Pair("매우 못함", 23),
            Pair("못함", 15),
            Pair("보통", 31),
            Pair("잘함", 4),
            Pair("매우 잘함", 3)
        ),
        colorList = DivergingStackedBarChartColors
    )
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DivergingStackedBarChart(
    modifier: Modifier = Modifier.Companion,
    dataList: List<Pair<String, Int>> = listOf(),
    colorList: List<Color> = DivergingStackedBarChartColors,
    chartHeight: Dp = 200.dp,
) {
    val valueSum = dataList.sumOf { (_, v) -> v }
    val xLabelList = listOf("100%","50%","0%","50%","100%")

    val stackedBarDataList = dataList.mapIndexed { index, data ->
        StackedBarData(
            name = data.first,
            value = data.second,
            percentage = data.second / valueSum.toFloat(),
            color = colorList[index]
        )
    }

    val leftPercentageSum = stackedBarDataList.take(stackedBarDataList.size/2).sumOf { data ->
        data.percentage.toDouble()
    }.toFloat() + (stackedBarDataList[stackedBarDataList.size/2].percentage / 2)


    val xLabelTextMeasurer = rememberTextMeasurer()
    val xLabelLayoutList = xLabelList.mapIndexed { index, data ->
        xLabelTextMeasurer.measure(
            AnnotatedString(data),
            style = TextStyle(fontSize = 17.sp, textAlign = TextAlign.Companion.Center)
        )
    }
    val maxHeightXLabel = xLabelLayoutList.maxOf { it.size.height }.toFloat()
    val maxWidthXLabel = xLabelLayoutList.maxOf { it.size.width }.toFloat()

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {

        Canvas(
            modifier = Modifier.Companion
                .fillMaxWidth()
                .height(chartHeight)
        ) {
            val xAxisOffsetY = size.height - maxHeightXLabel

            val barWidth = (size.width - maxWidthXLabel) / 2
            val barHeight = xAxisOffsetY / 2

            val graphCenterX = size.width / 2

            val xLabelPadding = (size.width - (maxWidthXLabel * xLabelList.size)) / (xLabelList.size - 1)
            var xLabelStartOffsetX = 0f
            xLabelLayoutList.forEachIndexed { index, layout ->

                drawLine(
                    color = Color.Companion.Black,
                    start = Offset(xLabelStartOffsetX + maxWidthXLabel / 2, 0f),
                    end = Offset(xLabelStartOffsetX + maxWidthXLabel / 2, xAxisOffsetY),
                )
                drawText(
                    textLayoutResult = layout,
                    topLeft = Offset(
                        x = xLabelStartOffsetX + (maxWidthXLabel / 2 - layout.size.width / 2),
                        y = xAxisOffsetY
                    )
                )

                xLabelStartOffsetX += maxWidthXLabel + xLabelPadding
            }

            var barStartOffsetX = graphCenterX - (barWidth * leftPercentageSum)
            stackedBarDataList.forEachIndexed { index, data ->
                drawRect(
                    color = data.color,
                    size = Size(barWidth * data.percentage, barHeight),
                    topLeft = Offset(
                        x = barStartOffsetX,
                        y = xAxisOffsetY / 2 - barHeight / 2,
                    )
                )
                barStartOffsetX += barWidth * data.percentage
            }
        }

        Spacer(
            modifier = Modifier.Companion
                .size(10.dp)
        )


        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(
                10.dp,
                Alignment.Companion.CenterHorizontally
            ),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            stackedBarDataList.forEachIndexed { index, data ->
                LabelText(
                    labelText = data.name,
                    labelColor = data.color,
                    labelPadding = 5
                )
            }
        }

    }


}