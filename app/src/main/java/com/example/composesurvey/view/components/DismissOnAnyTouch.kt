package com.example.composesurvey.view.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.changedToDown
import androidx.compose.ui.input.pointer.pointerInput


@Composable
fun DismissOnAnyTouch(
    onTouchOutside: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while(true) {
                        val event = awaitPointerEvent(PointerEventPass.Initial)
                        if(event.changes.any {it.changedToDown()}) {
                            onTouchOutside()
                        }
                    }
                }
            }
    )
}