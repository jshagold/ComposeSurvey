package com.example.composesurvey.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.composesurvey.common.result.Result
import com.example.composesurvey.data.SurveyRepository
import com.example.composesurvey.data.exception.FileException
import com.example.composesurvey.data.exception.UnexpectedException
import com.example.composesurvey.model.SurveyPreview
import com.example.composesurvey.view.error.ErrorCode
import com.example.composesurvey.view.state.SurveyListState
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
                val resultSurveyList: List<Result<SurveyPreview>> = surveyRepository.getSurveyTitleList()

                _surveyListState.update {
                    it.copy(
                        titleList = resultSurveyList
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