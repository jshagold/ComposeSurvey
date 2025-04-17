package com.example.composesurvey.view.components

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
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.composesurvey.data.model.Answer
import com.example.composesurvey.data.model.Question
import com.example.composesurvey.data.model.QuestionType
import com.example.composesurvey.view.model.AnswerUI
import com.example.composesurvey.view.model.QuestionAndAnswerUI
import com.example.composesurvey.view.model.QuestionUI


@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun PreviewQuestionSingleChoice() {
    val question = QuestionUI(
        id = "q2",
        type = QuestionType.SINGLE_CHOICE,
        question = "가장 많이 사용하는 언어는 무엇인가요?",
        required = true,
        options = listOf("Kotlin", "Java", "C++", "Python")
    )

    val qna = QuestionAndAnswerUI(
        question = question,
        answer = AnswerUI.SingleChoice("Kotlin")
    )

    val rem = remember { mutableStateOf(qna) }

    QuestionSingleChoice(
        index = 1,
        qNA = rem.value,
        onClickCheckBox = { key ->
            rem.value = QuestionAndAnswerUI(
                question = question,
                answer = AnswerUI.SingleChoice(key)
            )
        }
    )
}


@Composable
fun QuestionSingleChoice(
    modifier: Modifier = Modifier,
    index: Int,
    qNA: QuestionAndAnswerUI,
    onClickCheckBox: (key: String) -> Unit = {}
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
    ) {
        val (questionIndex, questionTitle, selectedBox) = createRefs()
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
            text = qNA.question.question,
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

        RadioBtnBoxList(
            selectedList = qNA.question.options!!,
            checkedItem = (qNA.answer as AnswerUI.SingleChoice).selected,
            onClickCheckBox = onClickCheckBox,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
                .constrainAs(selectedBox) {
                    top.linkTo(barrier)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
    }
}