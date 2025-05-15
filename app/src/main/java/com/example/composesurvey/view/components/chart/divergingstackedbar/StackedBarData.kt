package com.example.composesurvey.view.components.chart.divergingstackedbar

import androidx.compose.ui.graphics.Color

data class StackedBarData(
    val name: String,
    val value: Int,
    val percentage: Float,
    val color: Color,
)
