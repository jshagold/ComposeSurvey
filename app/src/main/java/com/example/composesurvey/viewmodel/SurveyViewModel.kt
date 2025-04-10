package com.example.composesurvey.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.composesurvey.data.SurveyRepository
import com.example.composesurvey.model.Answer
import com.example.composesurvey.model.Question
import com.example.composesurvey.model.QuestionType
import com.example.composesurvey.view.state.SurveyCheckState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SurveyViewModel @Inject constructor(
    application: Application,
    val surveyRepository: SurveyRepository
) : AndroidViewModel(application) {


    private var _surveyCheckState: MutableStateFlow<SurveyCheckState> = MutableStateFlow(SurveyCheckState())
    val surveyCheckState: StateFlow<SurveyCheckState> = _surveyCheckState.asStateFlow()

    init {
        viewModelScope.launch {
            loadSurvey()
        }
    }




    fun loadSurvey() {
        val survey = surveyRepository.getSurvey()
        val qNAList: List<Pair<Question, Answer>> = survey.questions.map { question ->
            when(question.type) {
                QuestionType.TEXT -> {
                    Pair(question, Answer.Text(""))
                }
                QuestionType.SINGLE_CHOICE -> {
                    Pair(question, Answer.SingleChoice(""))
                }
                QuestionType.MULTIPLE_CHOICE -> {
                    Pair(question, Answer.MultipleChoice(listOf()))
                }
                QuestionType.SLIDER -> {
                    Pair(question, Answer.Slider(0))
                }
                QuestionType.LIKERT_SCALE -> {
                    Pair(question, Answer.LikertScale(0))
                }
            }
        }

        _surveyCheckState.update {
            it.copy(
                surveyTitle = survey.title,
                questionNAnswerList = qNAList
            )
        }
    }
}