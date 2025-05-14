package com.example.composesurvey.view.components.chart

import androidx.compose.ui.geometry.Offset
import kotlin.math.cos
import kotlin.math.sin

fun getCenterCoordinatesFromAngle(radius: Float, center: Offset, angle: Float): Offset {
    val radian = Math.toRadians(angle.toDouble())
    val x = center.x + cos(radian) * radius
    val y = center.y + sin(radian) * radius
    return Offset(x.toFloat(), y.toFloat())
}