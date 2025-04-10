package com.example.composesurvey.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun PreviewLikertScaleComponent() {
    val scaleList = listOf("매우 못함", "못함", "보통", "잘함", "매우 잘함")

    val answer = remember { mutableStateOf(2) }

    LikertScaleComponent(
        scaleList = scaleList,
        selectedIndex = answer.value,
        onClickValue = { index ->
            answer.value = index
        }
    )
}


@Composable
fun LikertScaleComponent(
    modifier: Modifier = Modifier,
    scaleList: List<String>,
    selectedIndex: Int,
    onClickValue: (index: Int) -> Unit = {}
) {
    Column(
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            scaleList.forEachIndexed { index, scale ->
                Text(
                    text = scale,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1f)
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
        ) {
            scaleList.forEachIndexed { index, scale ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .clickable {
                                onClickValue(index)
                            }
                            .size(30.dp)
                            .border(2.dp, Black)
                    ) {
                        if(selectedIndex == index) {
                            Box(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .size(30.dp)
                                    .background(Black)
                            )
                        }
                    }
                }
            }
        }
    }
}