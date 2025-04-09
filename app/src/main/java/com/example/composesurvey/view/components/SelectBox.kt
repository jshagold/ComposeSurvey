package com.example.composesurvey.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout


@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun PreviewSelectBox() {
    SelectBox(
        text = "MVVM",
        value = false
    )
}


@Composable
fun SelectBox(
    text: String = "",
    value: Boolean = false,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (checkBox, textLabel) = createRefs()

        Box(
            modifier = Modifier
                .clickable {
                    onClick()
                }
                .size(30.dp)
                .border(2.dp, Black)
                .constrainAs(checkBox) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(textLabel.start)
                }
        ) {
            if(value) {
                Box(
                    modifier = Modifier
                        .padding(5.dp)
                        .size(30.dp)
                        .background(Black)
                )
            }
        }

        Text(
            text = text,
            modifier = Modifier
                .padding(start = 10.dp)
                .constrainAs(textLabel) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(checkBox.end)
                    end.linkTo(parent.end)
                }
        )
    }
}