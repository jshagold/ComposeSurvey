package com.example.composesurvey.viewmodel

import android.R.id.message
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.result.Result
import com.example.domain.repository.SurveyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.aakira.napier.Napier
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
                    Napier.e(message = "Failed to update", throwable = result.cause)
                }
                is Result.Success -> {
                    Napier.d(message = "update successful")
                }
            }
        }
    }

}