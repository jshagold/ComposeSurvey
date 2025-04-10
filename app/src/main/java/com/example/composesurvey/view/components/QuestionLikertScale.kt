package com.example.composesurvey.view.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.composesurvey.model.Answer
import com.example.composesurvey.model.Question
import com.example.composesurvey.model.QuestionType


@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun PreviewQuestionLikertScale() {
    val question = Question(
        id = "q5",
        type = QuestionType.LIKERT_SCALE,
        question = "본인의 실력을 평가해 주세요.",
        scaleList = listOf("매우 못함", "못함", "보통", "잘함", "매우 잘함"),
    )

    val rem = remember { mutableStateOf(Pair(question, Answer.LikertScale(0))) }

    QuestionLikertScale(
        index = 2,
        qNA = rem.value,
        onValueChange = { value ->
            rem.value = Pair(question, Answer.LikertScale(value))
        }
    )
}

@Composable
fun QuestionLikertScale(
    modifier: Modifier = Modifier,
    index: Int,
    qNA: Pair<Question, Answer.LikertScale>,
    onValueChange: (value: Int) -> Unit = {}
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (questionIndex, questionTitle, likertScale) = createRefs()
        val barrier = createBottomBarrier(questionIndex, questionTitle)

        Text(
            text = "Q$index",
            modifier = Modifier
                .border(1.dp, Black, RoundedCornerShape(5.dp))
                .padding(10.dp)
                .constrainAs(questionIndex) {
                    top.linkTo(parent.top)
                    bottom.linkTo(barrier)
                    start.linkTo(parent.start)
                    end.linkTo(questionTitle.start)
                }
        )

        Text(
            text = qNA.first.question,
            modifier = Modifier
                .padding(start = 10.dp)
                .constrainAs(questionTitle) {
                    top.linkTo(parent.top)
                    bottom.linkTo(barrier)
                    start.linkTo(questionIndex.end)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        )

        LikertScaleComponent(
            scaleList = qNA.first.scaleList!!,
            selectedIndex = qNA.second.selected,
            onClickValue = onValueChange,
            modifier = Modifier
                .padding(top = 5.dp)
                .constrainAs(likertScale) {
                    top.linkTo(barrier)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
    }


}