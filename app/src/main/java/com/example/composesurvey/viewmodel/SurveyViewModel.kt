package com.example.composesurvey.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.composesurvey.data.SurveyRepository
import com.example.composesurvey.model.Answer
import com.example.composesurvey.model.Question
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SurveyViewModel @Inject constructor(
    application: Application,
    val surveyRepository: SurveyRepository
) : AndroidViewModel(application) {









    fun loadSurvey() {
        surveyRepository.getSurvey()


        val a = mapOf<Question, Answer>()

        a.toSortedMap { q1, q2 ->
            q1.id.length - q2.id.length
        }
    }
}