package com.example.composesurvey.view.components.chart

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path


fun roundRectPath(
    topLeft: Offset,
    size: Size,
    topStartRadius: Float = 0f,
    topEndRadius: Float = 0f,
    bottomStartRadius: Float = 0f,
    bottomEndRadius: Float = 0f,
): Path {
    val path = Path()
    val (x, y) = topLeft
    val w = size.width
    val h = size.height

    // 시작점: 좌상단 둥근 처리 시작점 or 직선 시작
    path.moveTo(x + topStartRadius, y)

    // 상단 라인 → 우상단
    path.lineTo(x + w - topEndRadius, y)
    if (topEndRadius > 0f) {
        path.quadraticTo(x + w, y, x + w, y + topEndRadius)
    }

    // 우측 라인 → 우하단
    path.lineTo(x + w, y + h - bottomEndRadius)
    if (bottomEndRadius > 0f) {
        path.quadraticTo(x + w, y + h, x + w - bottomEndRadius, y + h)
    }

    // 하단 라인 → 좌하단
    path.lineTo(x + bottomStartRadius, y + h)
    if (bottomStartRadius > 0f) {
        path.quadraticTo(x, y + h, x, y + h - bottomStartRadius)
    }

    // 좌측 라인 → 좌상단
    path.lineTo(x, y + topStartRadius)
    if (topStartRadius > 0f) {
        path.quadraticTo(x, y, x + topStartRadius, y)
    }

    path.close()
    return path
}