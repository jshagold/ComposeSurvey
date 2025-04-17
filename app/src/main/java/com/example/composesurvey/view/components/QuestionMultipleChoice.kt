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
fun PreviewQuestionMultipleChoice() {
    val question = QuestionUI(
        id = "q3",
        type = QuestionType.MULTIPLE_CHOICE,
        question = "사용해본 Android 아키텍처 패턴을 모두 선택하세요.",
        options = listOf("MVVM", "MVI", "MVC", "Clean Architecture", "MVP")
    )
    val qna = QuestionAndAnswerUI(
        question,
        AnswerUI.MultipleChoice(listOf("MVI", "MVP"))
    )
    val rem = remember { mutableStateOf(qna) }

    QuestionMultipleChoice(
        index = 1,
        qNA = rem.value,
        onClickCheckBox = { key ->
            val muList = (rem.value.answer as AnswerUI.MultipleChoice).selected.toMutableList()
            if(muList.contains(key)) {
                muList.remove(key)
            } else {
                muList.add(key)
            }

            rem.value = QuestionAndAnswerUI(
                question,
                AnswerUI.MultipleChoice(muList)
            )
        }
    )
}


@Composable
fun QuestionMultipleChoice(
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

        SelectBoxList(
            selectedList = qNA.question.options!!,
            checkedList = (qNA.answer as AnswerUI.MultipleChoice).selected,
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