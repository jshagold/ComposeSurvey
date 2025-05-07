package com.example.composesurvey.view.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.composesurvey.R


@Preview
@Composable
fun PreviewTextButtonDialog() {
    TextButtonDialog(stringResource(R.string.survey_result_saved_complete))
}

@Composable
fun TextButtonDialog(
    text: String,
    onClickConfirmBtn: () -> Unit = {},
) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        // dialogl 배경 dim
        val dialogWindowProvider = LocalView.current.parent as? DialogWindowProvider
        dialogWindowProvider?.window?.setDimAmount(0.5f)


        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .background(White, RoundedCornerShape(10.dp))
                .padding(20.dp)
        ) {
            val (contentTextComponent, confirmBtnComponent, cancelBtnComponent) = createRefs()

            Text(
                text = text,
                textAlign = TextAlign.Center,
                color = Black,
                modifier = Modifier
                    .constrainAs(contentTextComponent) {
                        top.linkTo(parent.top)
                        bottom.linkTo(confirmBtnComponent.top, 30.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
            )

            Button(
                onClick = onClickConfirmBtn,
                modifier = Modifier
                    .constrainAs(confirmBtnComponent) {
                        top.linkTo(contentTextComponent.bottom)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(cancelBtnComponent.end)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
            ) {
                Text(text = stringResource(id = R.string.common_ok))
            }
        }
    }
}