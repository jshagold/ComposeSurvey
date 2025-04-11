package com.example.composesurvey.view

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.composesurvey.viewmodel.SurveyViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.unit.dp
import com.example.composesurvey.model.Answer
import com.example.composesurvey.model.Question
import com.example.composesurvey.model.QuestionType
import com.example.composesurvey.view.components.QuestionLikertScale
import com.example.composesurvey.view.components.QuestionMultipleChoice
import com.example.composesurvey.view.components.QuestionSingleChoice
import com.example.composesurvey.view.components.QuestionSlider
import com.example.composesurvey.view.components.QuestionText
import com.example.composesurvey.view.state.SurveyCheckState


@Composable
fun SurveyCheckRoute(
    viewModel: SurveyViewModel = hiltViewModel()
) {
    val state by viewModel.surveyCheckState.collectAsStateWithLifecycle()

    SurveyCheckScreen(
        state = state
    )
}


@Composable
fun SurveyCheckScreen(
    modifier: Modifier = Modifier,
    state: SurveyCheckState
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Text(
            text = state.surveyTitle
        )

        state.questionNAnswerList.forEachIndexed { index, qNA ->
            when(val answer = qNA.second) {
                is Answer.Text -> {
                    QuestionText(
                        index = index,
                        qNA = Pair(qNA.first, answer),
                        modifier = Modifier
                            .padding(10.dp)
                    )
                }
                is Answer.SingleChoice -> {
                    QuestionSingleChoice(
                        index = index,
                        qNA = Pair(qNA.first, answer),
                        modifier = Modifier
                            .padding(10.dp)
                    )
                }
                is Answer.MultipleChoice -> {
                    QuestionMultipleChoice(
                        index = index,
                        qNA = Pair(qNA.first, answer),
                        modifier = Modifier
                            .padding(10.dp)
                    )
                }
                is Answer.Slider -> {
                    QuestionSlider(
                        index = index,
                        qNA = Pair(qNA.first, answer),
                        modifier = Modifier
                            .padding(10.dp)
                    )
                }
                is Answer.LikertScale -> {
                    QuestionLikertScale(
                        index = index,
                        qNA = Pair(qNA.first, answer),
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
    }
}