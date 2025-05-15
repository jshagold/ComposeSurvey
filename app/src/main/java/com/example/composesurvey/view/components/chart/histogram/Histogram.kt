package com.example.composesurvey.view.components.chart.histogram

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composesurvey.view.theme.GraphColors
import com.example.composesurvey.view.theme.GraphColorsType2


@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun PreviewHistogram() {
    Histogram(
        dataList = mapOf(
            "0" to 25,
            "1" to 24,
            "2" to 23,
            "3" to 22,
            "4" to 21,
            "5" to 20,
            "6" to 15,
            "7" to 10,
            "8" to 5,
            "9" to 2,
            "10" to 9,
        ),
        min = 0,
        max = 8,
    )
}


@Composable
fun Histogram(
    modifier: Modifier = Modifier,

    chartWidth: Dp = 400.dp,
    chartHeight: Dp = 200.dp,
    candleColor: Color = GraphColors[0],
    dataList: Map<String, Int> = mapOf(),
    min: Int,
    max: Int,
) {
    val maxCount = dataList.maxOf { (_, v) -> v }

    val yAxisPadding = 5.dp
    val xAxisPadding = 5.dp
    val candleHorizontalPadding = 5.dp


    Box(
        modifier = modifier
            .width(chartWidth)
            .height(chartHeight)
    ) {
        val maxYValue = (maxCount + 4) / 5 * 5
        val yLabelCount = (maxCount + 4) / 5 + 1
        val xLabelCount = max - min + 1

        val yLabelTextMeasurer = rememberTextMeasurer()
        val yLabelLayoutList = List(yLabelCount) { index ->
            yLabelTextMeasurer.measure(
                AnnotatedString((index*5).toString()),
                style = TextStyle(fontSize = 17.sp, textAlign = TextAlign.End)
            )
        }.reversed()
        val maxWidthYLabel = yLabelLayoutList.maxOf { it.size.width }.toFloat()
        val maxHeightYLabel = yLabelLayoutList.maxOf { it.size.height }.toFloat()

        val xLabelTextMeasurer = rememberTextMeasurer()
        val xLabelLayoutList = List(xLabelCount) { index ->
            xLabelTextMeasurer.measure(
                AnnotatedString(index.toString()),
                style = TextStyle(fontSize = 17.sp, textAlign = TextAlign.Center)
            )
        }
        val maxWidthXLabel = xLabelLayoutList.maxOf { it.size.width }.toFloat()
        val maxHeightXLabel = xLabelLayoutList.maxOf { it.size.height }.toFloat()



        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            /**
             * y label count = maxYValue / 5
             * x lable count = max - min + 1
             *
             * y label measure / x label measure
             *
             * y label padding / x label padding
             * candleHorizontalPadding
             *
             * size width = y label width + (x label width * x label count) + (x label padding * (x label count - 1)) + candleHorizontalPadding * 2
             * size height = x label height + (y label height * y label count) + (y label padding * (y label count - 1))
             */

            val xAxisOffsetY = size.height - (maxHeightXLabel + xAxisPadding.toPx())
            val xAxisEndOffsetX = size.width
            val yAxisLength = xAxisOffsetY - maxHeightYLabel/2


            // 세로축 라벨
            var yLabelStartOffsetY = 0f
            yLabelLayoutList.forEachIndexed { index, layout ->

                val yLabelPadding =  (yAxisLength + maxHeightYLabel - (maxHeightYLabel * yLabelLayoutList.size)) / (yLabelLayoutList.size - 1)

                // 세로축 경계선
                drawLine(
                    color = Color.LightGray,
                    start = Offset(maxWidthYLabel + yAxisPadding.toPx(), maxHeightYLabel/2 + yLabelStartOffsetY),
                    end = Offset(xAxisEndOffsetX, maxHeightYLabel/2 + yLabelStartOffsetY)
                )

                // 세로축 라벨 텍스트
                val startOffset = Offset(
                    x = maxWidthYLabel - layout.size.width,
                    y = yLabelStartOffsetY,
                )
                drawText(layout, topLeft = startOffset)

                yLabelStartOffsetY += maxHeightYLabel + yLabelPadding
            }


            // 가로축 라벨
            var xLabelOffsetX = maxWidthYLabel + candleHorizontalPadding.toPx() + candleHorizontalPadding.toPx()
            xLabelLayoutList.forEachIndexed { index, layout ->

                val labelWidth =  (xAxisEndOffsetX - maxWidthYLabel - yAxisPadding.toPx() - (candleHorizontalPadding.toPx() * 2)) / xLabelLayoutList.size

                val startOffset = Offset(
                    x = xLabelOffsetX + (labelWidth/2 - layout.size.width/2),
                    y = xAxisOffsetY + xAxisPadding.toPx(),
                )
                drawText(layout, topLeft = startOffset)


                // 가로축 막대그래프
                val candleHeight = dataList.getOrDefault(index.toString(), 0) / maxYValue.toFloat() * yAxisLength
                drawRect(
                    color = candleColor,
                    size = Size(labelWidth, candleHeight),
                    topLeft = Offset(
                        x = xLabelOffsetX,
                        y = maxHeightYLabel/2 + yAxisLength - candleHeight
                    ),
                )
                drawRect(
                    color = Color.Black,
                    size = Size(labelWidth, candleHeight),
                    topLeft = Offset(
                        x = xLabelOffsetX,
                        y = maxHeightYLabel/2 + yAxisLength - candleHeight
                    ),
                    style = Stroke(1f)
                )

                xLabelOffsetX += labelWidth
            }


            // 세로축 YAxis
            drawLine(
                color = Color.Black,
                start = Offset(maxWidthYLabel + yAxisPadding.toPx(), maxHeightYLabel/2),
                end = Offset(maxWidthYLabel + yAxisPadding.toPx(), xAxisOffsetY),
                strokeWidth = 2f
            )

            // 가로축 XAxis
            drawLine(
                color = Color.Black,
                start = Offset(maxWidthYLabel + yAxisPadding.toPx(), xAxisOffsetY),
                end = Offset(xAxisEndOffsetX, xAxisOffsetY),
                strokeWidth = 2f
            )
        }
    }

}