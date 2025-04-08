package com.example.composesurvey.view.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun PreviewQuestionMultipleChoice() {
    val question = Question(
        id = "q3",
        type = QuestionType.MULTIPLE_CHOICE,
        question = "사용해본 Android 아키텍처 패턴을 모두 선택하세요.",
        options = listOf("MVVM", "MVI", "MVC", "Clean Architecture")
    )

    QuestionMultipleChoice(
        index = 1,
        qNA = Pair(question, Answer.MultipleChoice(listOf())),
    )
}


@Composable
fun QuestionMultipleChoice(
    modifier: Modifier = Modifier,
    index: Int,
    qNA: Pair<Question, Answer.MultipleChoice>,
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
    ) {
        val (questionIndex, questionTitle, selectedBox) = createRefs()

        Text(
            text = "Q$index",
            modifier = Modifier
                .border(1.dp, Black, RoundedCornerShape(5.dp))
                .padding(10.dp)
                .constrainAs(questionIndex) {
                    top.linkTo(parent.top)
                    bottom.linkTo(selectedBox.top)
                    start.linkTo(parent.start)
                    end.linkTo(questionTitle.start)
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

        SelectBoxList(
            selectedList = qNA.first.options!!,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(selectedBox) {
                    top.linkTo(questionIndex.bottom)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
    }
}