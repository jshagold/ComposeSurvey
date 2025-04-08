package com.example.composesurvey.view.components

import android.R.attr.paddingHorizontal
import android.R.attr.paddingVertical
import android.R.attr.text
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.composesurvey.model.Answer
import com.example.composesurvey.model.Question
import com.example.composesurvey.model.QuestionType


@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun PreviewQuestionText() {
    val question = Question(
        id = "q1",
        type = QuestionType.TEXT,
        question = "당신의 이름은?",
        required = true
    )

    QuestionText(
        index = 0,
        qNA = Pair(question, Answer.Text("")),
    )
}


@Composable
fun QuestionText(
    modifier: Modifier = Modifier,
    index: Int,
    qNA: Pair<Question, Answer.Text>,
    onTextChange: (value: String) -> Unit = {},
) {

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
    ) {
        val (questionIndex, questionTitle, inputBox) = createRefs()

        Text(
            text = "Q$index",
            modifier = Modifier
                .border(1.dp, Black, RoundedCornerShape(5.dp))
                .padding(10.dp)
                .constrainAs(questionIndex) {
                    top.linkTo(parent.top)
                    bottom
                    start.linkTo(parent.start)
                    end
                }
        )

        Text(
            text = qNA.first.question,
            modifier = Modifier
                .padding(start = 10.dp)
                .constrainAs(questionTitle) {
                    top.linkTo(questionIndex.top)
                    bottom.linkTo(questionIndex.bottom)
                    start.linkTo(questionIndex.end)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        )

        EditText(
            inputText = qNA.second.value,
            onValueChange = onTextChange,
            placeholder = "...",
            borderSize = 1.dp,
            borderColor = Black,
            paddingVertical = 10.dp,
            paddingHorizontal = 10.dp,
            modifier = Modifier
                .padding(top = 5.dp)
                .constrainAs(inputBox) {
                    top.linkTo(questionIndex.bottom)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        )
    }
}