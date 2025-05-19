package com.example.composesurvey.view.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview


@Preview
@Composable
fun PreviewCircularProgress() {
    Column {
        var gauge = remember { Animatable(initialValue = 0f) }

        LaunchedEffect(Unit) {
            gauge.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 1000)
            )
        }



        CircularProgress(
            modifier = Modifier.fillMaxWidth(),
            value = gauge.value
        )

        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )
    }

}


@Composable
fun CircularProgress(
    modifier: Modifier = Modifier,
    value: Float
) {

    Canvas(
        modifier = modifier
            .aspectRatio(1f)
    ) {
        drawArc(
            color = Color.Blue,
            startAngle = -90f,
            sweepAngle = 360f * value,
            topLeft = Offset(0f, 0f),
            useCenter = false,
            style = Stroke(width = 10f)
        )
    }

}