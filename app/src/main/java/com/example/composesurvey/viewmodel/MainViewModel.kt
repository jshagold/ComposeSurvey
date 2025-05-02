package com.example.composesurvey.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.result.Result
import com.example.domain.repository.SurveyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    private val surveyRepository: SurveyRepository
) : AndroidViewModel(application) {


    fun updateSurvey() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = surveyRepository.saveSurveyToDBFromFile()

            when(result) {
                is Result.Failure -> {
                    Log.e("saveSurveyToDBFromFile", "updateSurvey: Failure ${result.cause?.printStackTrace()}", )
                }
                is Result.Success -> {
                    Log.d("TAG", "updateSurvey: Success")
                }
            }
        }
    }

}