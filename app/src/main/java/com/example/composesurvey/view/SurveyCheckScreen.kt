package com.example.composesurvey.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.composesurvey.data.model.Answer
import com.example.composesurvey.view.components.QuestionLikertScale
import com.example.composesurvey.view.components.QuestionMultipleChoice
import com.example.composesurvey.view.components.QuestionSingleChoice
import com.example.composesurvey.view.components.QuestionSlider
import com.example.composesurvey.view.components.QuestionText
import com.example.composesurvey.view.model.AnswerUI
import com.example.composesurvey.view.state.SurveyCheckState
import com.example.composesurvey.viewmodel.SurveyViewModel


@Composable
fun SurveyCheckRoute(
    viewModel: SurveyViewModel = hiltViewModel()
) {
    val state by viewModel.surveyCheckState.collectAsStateWithLifecycle()

    SurveyCheckScreen(
        state = state,
        questionTextChange = viewModel::questionTextChange,
        questionSingleChoiceChange = viewModel::questionSingleChoiceChange,
        questionMultipleChoiceChange = viewModel::questionMultipleChoiceChange,
        questionSliderChange = viewModel::questionSliderChange,
        questionLikertScaleChange = viewModel::questionLikertScaleChange,
        saveResult = viewModel::saveSurveyResult
    )
}


@Composable
fun SurveyCheckScreen(
    modifier: Modifier = Modifier,
    state: SurveyCheckState,
    questionTextChange: (questionIndex: Int, text: String) -> Unit = { _, _ -> },
    questionSingleChoiceChange: (questionIndex: Int, key: String) -> Unit = { _, _ -> },
    questionMultipleChoiceChange: (questionIndex: Int, key: String) -> Unit = { _, _ -> },
    questionSliderChange: (questionIndex: Int, value: Int) -> Unit = { _, _ -> },
    questionLikertScaleChange: (questionIndex: Int, valueIndex: Int) -> Unit = { _, _ -> },
    saveResult: () -> Unit = {},
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(White)
    ) {
        Text(
            text = state.surveyTitle
        )

        state.questionNAnswerList.forEachIndexed { index, qNA ->
            when (qNA.answer) {
                is AnswerUI.Text -> {
                    QuestionText(
                        index = index,
                        qNA = qNA,
                        onTextChange = { questionTextChange(index, it) },
                        modifier = Modifier
                            .padding(10.dp)
                    )
                }

                is AnswerUI.SingleChoice -> {
                    QuestionSingleChoice(
                        index = index,
                        qNA = qNA,
                        onClickCheckBox = { questionSingleChoiceChange(index, it) },
                        modifier = Modifier
                            .padding(10.dp)
                    )
                }

                is AnswerUI.MultipleChoice -> {
                    QuestionMultipleChoice(
                        index = index,
                        qNA = qNA,
                        onClickCheckBox = { questionMultipleChoiceChange(index, it) },
                        modifier = Modifier
                            .padding(10.dp)
                    )
                }

                is AnswerUI.Slider -> {
                    QuestionSlider(
                        index = index,
                        qNA = qNA,
                        onValueChange = { questionSliderChange(index, it) },
                        modifier = Modifier
                            .padding(10.dp)
                    )
                }

                is AnswerUI.LikertScale -> {
                    QuestionLikertScale(
                        index = index,
                        qNA = qNA,
                        onValueChange = { questionLikertScaleChange(index, it) },
                        modifier = Modifier
                            .padding(10.dp)
                    )
                }
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
                    .background(Gray)
            )
        }

        Button(
            onClick = saveResult,
            modifier = Modifier
                .align(Alignment.End)
        ) {
            Text(text = "제출")
        }
    }
}