package com.example.composesurvey.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.composesurvey.mapper.toUI
import com.example.composesurvey.model.QuestionWithAnswerUI
import com.example.domain.repository.SurveyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SurveyStatisticsViewModel @Inject constructor(
    application: Application,
    savedStateHandle: SavedStateHandle,
    private val surveyRepository: SurveyRepository
) : AndroidViewModel(application) {

    private val routeArgument: String = savedStateHandle["surveyId"] ?: "0"
    private val surveyId: Long = routeArgument.toLong()

    var qnaList: List<QuestionWithAnswerUI> = listOf()

    private var _uiState: MutableStateFlow<List<QuestionWithAnswerUI>> = MutableStateFlow(listOf())
    val uiState: StateFlow<List<QuestionWithAnswerUI>> = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                surveyRepository.getQuestionAndAnswerListBySurveyId(surveyId = surveyId).map { it.toUI() }
            }
        }
    }


}