package com.example.composesurvey.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.composesurvey.mapper.toUI
import com.example.composesurvey.model.SurveyUI
import com.example.composesurvey.view.error.ErrorCode
import com.example.composesurvey.view.state.SurveyListState
import com.example.core.exception.FileException
import com.example.core.exception.UnexpectedException
import com.example.core.result.Result
import com.example.domain.model.Survey as SurveyDomain
import com.example.domain.model.SurveyPreview
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
                            Log.e("${this@SurveyListViewModel.javaClass}", "init getSurveyTitleList: ${survey.cause?.printStackTrace()}", )
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
                Log.e("TAG", "init: ${e.printStackTrace()}", )
                _surveyListState.update {
                    it.copy(
                        errorCode = ErrorCode.FILE
                    )
                }
            } catch (e: UnexpectedException) {
                Log.e("TAG", "init: ${e.printStackTrace()}", )
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

}