package com.example.composesurvey.view.components.chart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension


@Preview
@Composable
fun PreviewLabelText() {
    LabelText(
        labelText = "Clean Architecture",
        labelColor = Color.Blue,
        labelPadding = 10
    )
}


@Composable
fun LabelText(
    modifier: Modifier = Modifier,
    labelText: String = "",
    labelColor: Color = Color.Transparent,
    labelPadding: Int = 0
) {

    ConstraintLayout(
        modifier = modifier
    ) {

        val (colorBox, label) = createRefs()

        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .background(labelColor, RoundedCornerShape(5.dp))
                .constrainAs(colorBox) {
                    top.linkTo(label.top)
                    bottom.linkTo(label.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(label.start, labelPadding.dp)
                    height = Dimension.fillToConstraints
                }
        )

        Text(
            text = labelText,
            modifier = Modifier
                .constrainAs(label) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(colorBox.end)
                    end.linkTo(parent.end)
                }
        )
    }
}