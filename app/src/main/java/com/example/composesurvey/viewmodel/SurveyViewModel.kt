package com.example.composesurvey.viewmodel

import android.app.Application
import android.util.Log
import android.util.Log.e
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.composesurvey.data.SurveyRepository
import com.example.composesurvey.data.exception.FileException
import com.example.composesurvey.data.exception.UnexpectedException
import com.example.composesurvey.model.Answer
import com.example.composesurvey.model.Question
import com.example.composesurvey.model.QuestionType
import com.example.composesurvey.model.Survey
import com.example.composesurvey.view.error.ErrorCode
import com.example.composesurvey.view.state.SurveyCheckState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SurveyViewModel @Inject constructor(
    application: Application,
    savedStateHandle: SavedStateHandle,
    private val surveyRepository: SurveyRepository
) : AndroidViewModel(application) {

    private val surveyTitle: String = savedStateHandle["fileName"] ?: ""

    private var _surveyCheckState: MutableStateFlow<SurveyCheckState> =
        MutableStateFlow(SurveyCheckState())
    val surveyCheckState: StateFlow<SurveyCheckState> = _surveyCheckState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            loadSurvey(surveyTitle)
        }
    }

    private fun loadSurvey(fileName: String) {
        try {
            val survey: Survey = surveyRepository.getSurvey(fileName)

            val qNAList: List<Pair<Question, Answer>> = survey.questions.map { question ->
                when (question.type) {
                    QuestionType.TEXT -> {
                        Pair(question, Answer.Text(""))
                    }

                    QuestionType.SINGLE_CHOICE -> {
                        if (question.options == null) throw FileException(msg = "json element error - single choice")
                        Pair(question, Answer.SingleChoice(""))
                    }

                    QuestionType.MULTIPLE_CHOICE -> {
                        if (question.options == null) throw FileException(msg = "json element error - multiple choice")
                        Pair(question, Answer.MultipleChoice(listOf()))
                    }

                    QuestionType.SLIDER -> {
                        if (question.min == null && question.max == null) throw FileException(msg = "json element error - slider")
                        Pair(question, Answer.Slider(0))
                    }

                    QuestionType.LIKERT_SCALE -> {
                        if (question.scaleList == null) throw FileException(msg = "json element error - likert scale")
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
        } catch (e: FileException) {
            e("TAG", "loadSurvey: ${e.printStackTrace()}")
            _surveyCheckState.update {
                it.copy(
                    errorCode = ErrorCode.FILE
                )
            }
        } catch (e: UnexpectedException) {
            e("TAG", "loadSurvey: ${e.printStackTrace()}")
            _surveyCheckState.update {
                it.copy(
                    errorCode = ErrorCode.UNEXPECTED
                )
            }
        }
    }


    fun questionTextChange(questionIndex: Int, text: String) {
        val list = surveyCheckState.value.questionNAnswerList.mapIndexed { index, pair ->
            if (index == questionIndex) {
                Pair(pair.first, Answer.Text(text))
            } else {
                pair
            }
        }

        _surveyCheckState.update {
            it.copy(
                questionNAnswerList = list
            )
        }
    }

    fun questionSingleChoiceChange(questionIndex: Int, key: String) {
        val list = surveyCheckState.value.questionNAnswerList.mapIndexed { index, pair ->
            if (index == questionIndex) {
                Pair(pair.first, Answer.SingleChoice(key))
            } else {
                pair
            }
        }

        _surveyCheckState.update {
            it.copy(
                questionNAnswerList = list
            )
        }
    }

    fun questionMultipleChoiceChange(questionIndex: Int, key: String) {
        val list = surveyCheckState.value.questionNAnswerList.mapIndexed { index, pair ->
            if (index == questionIndex) {
                val answer = pair.second as Answer.MultipleChoice
                val muList = answer.selected.toMutableList()
                if (muList.contains(key)) {
                    muList.remove(key)
                } else {
                    muList.add(key)
                }

                Pair(pair.first, Answer.MultipleChoice(muList))
            } else {
                pair
            }
        }

        _surveyCheckState.update {
            it.copy(
                questionNAnswerList = list
            )
        }
    }

    fun questionSliderChange(questionIndex: Int, value: Int) {
        val list = surveyCheckState.value.questionNAnswerList.mapIndexed { index, pair ->
            if (index == questionIndex) {
                Pair(pair.first, Answer.Slider(value))
            } else {
                pair
            }
        }

        _surveyCheckState.update {
            it.copy(
                questionNAnswerList = list
            )
        }
    }

    fun questionLikertScaleChange(questionIndex: Int, value: Int) {
        val list = surveyCheckState.value.questionNAnswerList.mapIndexed { index, pair ->
            if (index == questionIndex) {
                Pair(pair.first, Answer.LikertScale(value))
            } else {
                pair
            }
        }

        _surveyCheckState.update {
            it.copy(
                questionNAnswerList = list
            )
        }
    }
}