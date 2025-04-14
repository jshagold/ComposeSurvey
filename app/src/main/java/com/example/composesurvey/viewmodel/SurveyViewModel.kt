package com.example.composesurvey.viewmodel

import android.R.attr.text
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.composesurvey.data.SurveyRepository
import com.example.composesurvey.data.exception.FileException
import com.example.composesurvey.data.exception.UnexpectedException
import com.example.composesurvey.model.Answer
import com.example.composesurvey.model.Question
import com.example.composesurvey.model.QuestionType
import com.example.composesurvey.model.Survey
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

    private fun loadSurvey() {

        val surveyList: List<Survey> = try {
            surveyRepository.getSurveyList()
        } catch (e: FileException) {
            Log.e("TAG", "loadSurvey: $e", )
            listOf()
        } catch (e: UnexpectedException) {
            Log.e("TAG", "loadSurvey: $e", )
            listOf()
        }


        if(surveyList.isNotEmpty()) {
            val qNAList: List<Pair<Question, Answer>> = surveyList[0].questions.map { question ->
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

            Log.e("TAG", "loadSurvey: $qNAList", )

            _surveyCheckState.update {
                it.copy(
                    surveyTitle = surveyList[0].title,
                    questionNAnswerList = qNAList
                )
            }
        } else {
            Log.e("TAG", "loadSurvey: Empty Survey", )
        }
    }



    fun questionTextChange(questionIndex: Int, text: String) {
        val list = surveyCheckState.value.questionNAnswerList.mapIndexed { index, pair ->
            if(index == questionIndex) {
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
            if(index == questionIndex) {
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
            if(index == questionIndex) {
                val answer = pair.second as Answer.MultipleChoice
                val muList = answer.selected.toMutableList()
                if(muList.contains(key)) {
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
            if(index == questionIndex) {
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
            if(index == questionIndex) {
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