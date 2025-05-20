package com.example.composesurvey.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.composesurvey.mapper.toUI
import com.example.composesurvey.model.SurveyUI
import com.example.composesurvey.error.ErrorCode
import com.example.composesurvey.view.state.SurveyListState
import com.example.core.exception.FileException
import com.example.core.exception.UnexpectedException
import com.example.core.result.Result
import com.example.domain.repository.SurveyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.domain.model.Survey as SurveyDomain

@HiltViewModel
class SurveyListViewModel @Inject constructor(
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

                _surveyListState.update {
                    it.copy(
                        surveyList = resultSurveyList
                    )
                }
            } catch (e: FileException) {
                Napier.e(message = "init", throwable = e)
                _surveyListState.update {
                    it.copy(
                        errorCode = ErrorCode.FILE
                    )
                }
            } catch (e: UnexpectedException) {
                Napier.e(message = "init", throwable = e)
                _surveyListState.update {
                    it.copy(
                        errorCode = ErrorCode.UNEXPECTED
                    )
                }
            }
        }
    }

    private var _surveyListState: MutableStateFlow<SurveyListState> = MutableStateFlow(SurveyListState())
    val surveyListState: StateFlow<SurveyListState> = _surveyListState.asStateFlow()

    val pagingSurveyList: StateFlow<PagingData<SurveyUI>> =
        surveyRepository.getSurveyListByPage(10)
            .map { pagingData ->
                pagingData.map { surveyDomain ->
                    surveyDomain.toUI()
                }
            }
            .cachedIn(viewModelScope)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = PagingData.empty()
            )

}