package com.example.composesurvey.view.components.chart.histogram

import android.R.attr.x
import android.R.attr.y
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun PreviewHistogram() {
    Histogram(
        dataList = listOf(
            Pair("0", 23),
            Pair("3", 15),
            Pair("7", 9),
            Pair("8", 4),
        ),
        min = 0,
        max = 10,
    )
}


@Composable
fun Histogram(
    modifier: Modifier = Modifier,

    dataList: List<Pair<String, Int>> = listOf(),
    min: Int,
    max: Int,

) {
    val maxCount = dataList.maxOf { pair -> pair.second }

    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        val maxYValue = (maxCount + 4) / 5 * 5
        val yLabelCount = (maxCount + 4) / 5
        val xLabelCount = max - min + 1

        val yLabelTextMeasurer = rememberTextMeasurer()
        val yLabelLayoutList = List(yLabelCount) { index ->
            yLabelTextMeasurer.measure(
                AnnotatedString((index*5).toString()),
                style = TextStyle(fontSize = 10.sp, textAlign = TextAlign.End)
            )
        }.reversed()
        val maxWidthYLabel = yLabelLayoutList.maxOf { it.size.width }.toFloat()

        val xLabelTextMeasurer = rememberTextMeasurer()
        val xLabelLayoutList = List(xLabelCount) { index ->
            xLabelTextMeasurer.measure(
                AnnotatedString(index.toString()),
                style = TextStyle(fontSize = 10.sp, textAlign = TextAlign.Center)
            )
        }
        val maxWidthXLabel = xLabelLayoutList.maxOf { it.size.width }.toFloat()

        val yLabelPadding = 3.dp
        val xLabelPadding = 3.dp
        val candleHorizontalPadding = 5.dp


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

            // 세로축 라벨
            var yLabelOffsetY = 0f
            yLabelLayoutList.forEachIndexed { index, layout ->
                val startOffset = Offset(
                    x = maxWidthYLabel/2 - layout.size.width/2,
                    y = yLabelOffsetY,
                )
                drawText(layout, topLeft = startOffset)
                yLabelOffsetY += layout.size.height + yLabelPadding.toPx()

                // 세로축 경계선
                drawLine(Color.LightGray, start = Offset(maxWidthYLabel,yLabelOffsetY), end = Offset((maxWidthXLabel + xLabelPadding.toPx())*xLabelLayoutList.size,yLabelOffsetY))
            }

            // 세로축 경계선
            drawLine(Color.Black, start = Offset(maxWidthYLabel,0f), end = Offset(maxWidthYLabel,yLabelOffsetY))



            // 가로축 라벨
            var xLabelOffsetX = maxWidthYLabel
            xLabelLayoutList.forEachIndexed { index, layout ->

                val startOffset = Offset(
                    x = xLabelOffsetX + (maxWidthXLabel/2 - layout.size.width/2),
                    y = yLabelOffsetY,
                )
                drawText(layout, topLeft = startOffset)
                xLabelOffsetX += maxWidthXLabel + xLabelPadding.toPx()

                // 가로축 막대그래프
            }

            // 가로축 라벨
            drawLine(Color.Black, start = Offset(maxWidthYLabel,yLabelOffsetY), end = Offset(xLabelOffsetX,yLabelOffsetY),)
        }
    }

}