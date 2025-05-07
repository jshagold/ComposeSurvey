package com.example.composesurvey.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.SurveyRepository
import com.example.domain.model.QuestionAndAnswer
import com.example.domain.model.QuestionType
import com.example.domain.model.Survey
import com.example.composesurvey.view.converter.toQuestionAndAnswer
import com.example.composesurvey.view.converter.toQuestionUI
import com.example.composesurvey.view.error.ErrorCode
import com.example.composesurvey.model.AnswerUI
import com.example.composesurvey.model.QuestionAndAnswerUI
import com.example.composesurvey.view.state.SurveyCheckState
import com.example.core.exception.FileException
import com.example.core.exception.UnexpectedException
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
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

    private val routeArgument: String = savedStateHandle["surveyId"] ?: "0"
    private val surveyId: Long = routeArgument.toLong()

    private var _surveyCheckState: MutableStateFlow<SurveyCheckState> =
        MutableStateFlow(SurveyCheckState())
    val surveyCheckState: StateFlow<SurveyCheckState> = _surveyCheckState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            loadSurvey(surveyId)
        }
    }

    private fun loadSurvey(surveyId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val survey: Survey? = surveyRepository.getSurvey(surveyId)

                if(survey != null) {
                    val qNAList: List<QuestionAndAnswerUI> = survey.questions.map { question ->
                        when (question.type) {
                            QuestionType.TEXT -> {
                                QuestionAndAnswerUI(
                                    question = question.toQuestionUI(),
                                    answer = AnswerUI.Text("")
                                )
                            }

                            QuestionType.SINGLE_CHOICE -> {
                                if (question.options == null) throw FileException(msg = "json element null - single choice")
                                QuestionAndAnswerUI(
                                    question = question.toQuestionUI(),
                                    answer = AnswerUI.SingleChoice("")
                                )
                            }

                            QuestionType.MULTIPLE_CHOICE -> {
                                if (question.options == null) throw FileException(msg = "json element null - multiple choice")
                                QuestionAndAnswerUI(
                                    question = question.toQuestionUI(),
                                    answer = AnswerUI.MultipleChoice(listOf())
                                )
                            }

                            QuestionType.SLIDER -> {
                                if (question.min == null && question.max == null) throw FileException(msg = "json element null - slider")
                                QuestionAndAnswerUI(
                                    question = question.toQuestionUI(),
                                    answer = AnswerUI.Slider(0)
                                )
                            }

                            QuestionType.LIKERT_SCALE -> {
                                if (question.scaleList == null) throw FileException(msg = "json element null - likert scale")
                                QuestionAndAnswerUI(
                                    question = question.toQuestionUI(),
                                    answer = AnswerUI.LikertScale(0)
                                )
                            }
                        }
                    }

                    _surveyCheckState.update {
                        it.copy(
                            surveyTitle = survey.title,
                            questionNAnswerList = qNAList
                        )
                    }
                } else {
                    _surveyCheckState.update {
                        it.copy(
                            errorCode = ErrorCode.NULL_ELEMENT
                        )
                    }
                }

            } catch (e: FileException) {
                Napier.e(message = "data = $surveyId", throwable = e)
                _surveyCheckState.update {
                    it.copy(
                        errorCode = ErrorCode.FILE
                    )
                }
            } catch (e: UnexpectedException) {
                Napier.e(message = "data = $surveyId", throwable = e)
                _surveyCheckState.update {
                    it.copy(
                        errorCode = ErrorCode.UNEXPECTED
                    )
                }
            }
        }
    }


    fun questionTextChange(questionIndex: Int, text: String) {
        val list: List<QuestionAndAnswerUI> = surveyCheckState.value.questionNAnswerList.mapIndexed { index, qNA ->
            if (index == questionIndex) {
                qNA.copy(
                    answer = AnswerUI.Text(text)
                )
            } else {
                qNA
            }
        }

        _surveyCheckState.update {
            it.copy(
                questionNAnswerList = list
            )
        }
    }

    fun questionSingleChoiceChange(questionIndex: Int, key: String) {
        val list: List<QuestionAndAnswerUI> = surveyCheckState.value.questionNAnswerList.mapIndexed { index, qNA ->
            if (index == questionIndex) {
                qNA.copy(
                    answer = AnswerUI.SingleChoice(key)
                )
            } else {
                qNA
            }
        }

        _surveyCheckState.update {
            it.copy(
                questionNAnswerList = list
            )
        }
    }

    fun questionMultipleChoiceChange(questionIndex: Int, key: String) {
        val list: List<QuestionAndAnswerUI> = surveyCheckState.value.questionNAnswerList.mapIndexed { index, qNA ->
            if (index == questionIndex) {
                val answer = qNA.answer as AnswerUI.MultipleChoice
                val muList = answer.selected.toMutableList()
                if (muList.contains(key)) {
                    muList.remove(key)
                } else {
                    muList.add(key)
                }

                qNA.copy(
                    answer = AnswerUI.MultipleChoice(muList)
                )
            } else {
                qNA
            }
        }

        _surveyCheckState.update {
            it.copy(
                questionNAnswerList = list
            )
        }
    }

    fun questionSliderChange(questionIndex: Int, value: Int) {
        val list: List<QuestionAndAnswerUI> = surveyCheckState.value.questionNAnswerList.mapIndexed { index, qNA ->
            if (index == questionIndex) {
                qNA.copy(
                    answer = AnswerUI.Slider(value)
                )
            } else {
                qNA
            }
        }

        _surveyCheckState.update {
            it.copy(
                questionNAnswerList = list
            )
        }
    }

    fun questionLikertScaleChange(questionIndex: Int, value: Int) {
        val list: List<QuestionAndAnswerUI> = surveyCheckState.value.questionNAnswerList.mapIndexed { index, qNA ->
            if (index == questionIndex) {
                qNA.copy(
                    answer = AnswerUI.LikertScale(value)
                )
            } else {
                qNA
            }
        }

        _surveyCheckState.update {
            it.copy(
                questionNAnswerList = list
            )
        }
    }


    fun saveSurveyResult() {
        viewModelScope.launch(Dispatchers.IO) {
            val result: List<QuestionAndAnswer> = surveyCheckState.value.questionNAnswerList.map { qnaUI ->
                qnaUI.toQuestionAndAnswer()
            }

            try {
                surveyRepository.saveSurveyResult(result)
            } catch (e: Exception) {
                Log.e("TAG", "saveSurveyResult: ${e.printStackTrace()}", )
                Napier.e(message = "Failed to save data", throwable = e)
                _surveyCheckState.update {
                    it.copy(
                        errorCode = ErrorCode.DB
                    )
                }
            }
        }
    }
}