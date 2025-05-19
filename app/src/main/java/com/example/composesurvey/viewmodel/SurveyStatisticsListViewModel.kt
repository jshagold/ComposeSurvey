package com.example.composesurvey.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.composesurvey.mapper.toUI
import com.example.composesurvey.model.SurveyUI
import com.example.composesurvey.error.ErrorCode
import com.example.composesurvey.view.state.SurveyResultListState
import com.example.core.exception.FileException
import com.example.core.exception.UnexpectedException
import com.example.core.result.Result
import com.example.domain.repository.SurveyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.domain.model.Survey as SurveyDomain

@HiltViewModel
class SurveyStatisticsListViewModel @Inject constructor(
    application: Application,
    val surveyRepository: SurveyRepository
) : AndroidViewModel(application) {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val resultSurveyList: List<Result<SurveyUI>> = surveyRepository.getSurveyList().map { survey: Result<SurveyDomain> ->
                    when(survey) {
                        is Result.Failure -> {
                            Napier.e(message = "surveyRepository.getSurveyList() error data = $survey", throwable = survey.cause)
                            survey
                        }
                        is Result.Success<SurveyDomain> -> {
                            Result.Success(survey.data.toUI())
                        }
                    }
                }

                _surveyResultListState.update {
                    it.copy(
                        surveyList = resultSurveyList
                    )
                }
            } catch (e: FileException) {
                Napier.e(message = "init", throwable = e)
                _surveyResultListState.update {
                    it.copy(
                        errorCode = ErrorCode.FILE
                    )
                }
            } catch (e: UnexpectedException) {
                Napier.e(message = "init", throwable = e)
                _surveyResultListState.update {
                    it.copy(
                        errorCode = ErrorCode.UNEXPECTED
                    )
                }
            }
        }
    }

    private var _surveyResultListState: MutableStateFlow<SurveyResultListState> = MutableStateFlow(SurveyResultListState())
    val surveyResultListState: StateFlow<SurveyResultListState> = _surveyResultListState.asStateFlow()


}