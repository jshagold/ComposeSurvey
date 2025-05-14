package com.example.composesurvey.view.components.chart.pie

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.example.composesurvey.view.components.chart.LabelText
import com.example.composesurvey.view.components.chart.getCenterCoordinatesFromAngle
import com.example.composesurvey.view.theme.GraphColors
import kotlin.math.PI
import kotlin.math.atan2

@Preview
@Composable
fun PreviewPieChart() {
    var selectedData by remember { mutableStateOf(2) }

    PieChart(
        dataList = listOf(
            Pair("Kotlin", 23),
            Pair("Java", 15),
            Pair("C++", 9),
            Pair("Python", 4),
        ),
        selectedDataIndex = selectedData,
        onClickPie = {
            selectedData = it
        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    // name, count pair sorted by count
    dataList: List<Pair<String, Int>> = listOf(),
    colorList: List<Color> = GraphColors,
    selectedDataIndex: Int? = null,
    onClickPie: (index: Int) -> Unit = {}
) {
    val countSum = dataList.sumOf { it.second }

    val chartDataList: MutableList<PiePiece> = mutableListOf()
    var startAngle = -90f
    dataList.forEachIndexed { index, data ->
        chartDataList.add(
            PiePiece(
                color = colorList[index % dataList.size],
                startAngle = startAngle,
                sweepAngle = 360f * data.second / countSum,
                label = data.first,
                count = data.second,
            )
        )
        startAngle += 360f * data.second / countSum
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        val center = Offset(size.width / 2f, size.height / 2f)
                        val clickVector = offset - center
                        val clickDistance = clickVector.getDistance()

                        val angle = (atan2(clickVector.y, clickVector.x) * 180f / PI.toFloat()).let { if (it < -90f) it + 360f else it }

                        if (clickDistance > 0f && clickDistance <= size.width / 2) {
                            chartDataList.forEachIndexed { index, chartData ->
                                if (angle in chartData.startAngle..(chartData.startAngle + chartData.sweepAngle)) {
                                    onClickPie(index)
                                }
                            }
                        }

                    }
                }
        ) {
            var canvasSize by remember { mutableStateOf(Size.Zero) }
            var tooltipSize by remember { mutableStateOf(IntSize.Zero) }
            var measuredOnce by remember { mutableStateOf(false) }

            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .onGloballyPositioned { coordinates ->
                        val newSize = coordinates.size.toSize()
                        if(canvasSize != newSize) {
                            canvasSize = newSize
                        }
                    }
            ) {
                canvasSize = size

                chartDataList.forEach { data ->
                    drawArc(
                        color = data.color,
                        startAngle = data.startAngle,
                        sweepAngle = data.sweepAngle,
                        useCenter = true,
                        style = Fill
                    )
                }

                if(selectedDataIndex != null) {
                    drawArc(
                        color = Color.Blue,
                        startAngle = chartDataList[selectedDataIndex].startAngle,
                        sweepAngle = chartDataList[selectedDataIndex].sweepAngle,
                        useCenter = true,
                        style = Stroke(
                            width = 10f
                        )
                    )
                }
            }



            if(selectedDataIndex != null) {
                val selectedData = chartDataList[selectedDataIndex]
                val centerAngle = selectedData.startAngle + selectedData.sweepAngle / 2
                val targetOffset = getCenterCoordinatesFromAngle(canvasSize.width/4, Offset(canvasSize.width / 2f, canvasSize.height / 2f), centerAngle)

                Text(
                    text = "${chartDataList[selectedDataIndex].count}개",
                    modifier = Modifier
                        .onGloballyPositioned { coordinates ->
                            if(!measuredOnce) {
                                val newSize = coordinates.size
                                if(tooltipSize != newSize) {
                                    tooltipSize = newSize
                                }
                                measuredOnce = true
                            }
                        }
                        .offset {
                            IntOffset(targetOffset.x.toInt() - tooltipSize.width/2, targetOffset.y.toInt() - tooltipSize.height/2)
                        }
                        .background(Color.White, RoundedCornerShape(3.dp))
                        .border(1.dp, Color.Black, RoundedCornerShape(3.dp))
                        .padding(10.dp)
                )
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
            chartDataList.forEachIndexed { index, data ->
                LabelText(
                    labelText = data.label,
                    labelColor = data.color,
                    labelPadding = 10
                )
            }
        }

    }
}