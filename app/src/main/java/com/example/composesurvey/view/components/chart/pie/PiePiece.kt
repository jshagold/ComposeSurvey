package com.example.composesurvey.view.components.chart.pie

import androidx.compose.ui.graphics.Color

data class PiePiece(
    val color: Color = Color.Transparent,
    val sweepAngle: Float = 0f,
    var startAngle: Float = 0f,
    val label: String = "",
    val count: Int = 0
)
