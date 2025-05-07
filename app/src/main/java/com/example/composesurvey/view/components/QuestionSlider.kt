package com.example.composesurvey.view.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
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
import com.example.domain.model.Answer
import com.example.domain.model.Question
import com.example.domain.model.QuestionType
import com.example.composesurvey.model.AnswerUI
import com.example.composesurvey.model.QuestionAndAnswerUI
import com.example.composesurvey.model.QuestionTypeUI
import com.example.composesurvey.model.QuestionUI
import kotlin.math.round


@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun PreviewQuestionSlider() {
    val question = QuestionUI(
        id = 4,
        surveyId = 0,
        type = QuestionTypeUI.SLIDER,
        question = "경력 연차를 선택해주세요.",
        min = 0,
        max = 10
    )
    val qna = QuestionAndAnswerUI(
        question,
        AnswerUI.Slider(0)
    )
    val rem = remember { mutableStateOf(qna) }

    QuestionSlider(
        index = 2,
        question = rem.value.question,
        answer = rem.value.answer as AnswerUI.Slider,
        onValueChange = { value ->
            rem.value = QuestionAndAnswerUI(
                question,
                AnswerUI.Slider(value)
            )
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionSlider(
    modifier: Modifier = Modifier,
    index: Int,
    question: QuestionUI,
    answer: AnswerUI.Slider,
    onValueChange: (value: Int) -> Unit = {}
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
    ) {
        val (questionIndex, questionTitle, slider) = createRefs()
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
            text = question.question,
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

        Slider(
            value = answer.value.toFloat(),
            onValueChange = {
                onValueChange(round(it).toInt())
            },
            steps = (question.max!! - question.min!!) - 1,
            valueRange = question.min.toFloat()..question.max.toFloat(),
            thumb = {
                Text(
                    text = "${answer.value}년",
                    modifier = Modifier
                        .border(1.dp, Black, RoundedCornerShape(2.dp))
                        .padding(2.dp)
                )
            },
            modifier = Modifier
                .constrainAs(slider) {
                    top.linkTo(barrier)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        )
    }

}