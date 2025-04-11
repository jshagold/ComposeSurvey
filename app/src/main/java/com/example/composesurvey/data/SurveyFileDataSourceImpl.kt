package com.example.composesurvey.data

import android.content.Context
import com.example.composesurvey.model.Question
import com.example.composesurvey.model.QuestionType
import com.example.composesurvey.model.Survey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SurveyFileDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SurveyFileDataSource {

    // todo
    override fun loadSurvey(): Survey {
        return Survey(
            title = "Android 개발자 설문조사",
            questions = listOf(
                Question(
                    id = "q1",
                    type = QuestionType.TEXT,
                    question = "당신의 이름은?",
                    required = true
                ),
                Question(
                    id = "q2",
                    type = QuestionType.SINGLE_CHOICE,
                    question = "가장 많이 사용하는 언어는 무엇인가요?",
                    required = true,
                    options = listOf("Kotlin", "Java", "C++", "Python")
                ),
                Question(
                    id = "q3",
                    type = QuestionType.MULTIPLE_CHOICE,
                    question = "사용해본 Android 아키텍처 패턴을 모두 선택하세요.",
                    options = listOf("MVVM", "MVI", "MVC", "Clean Architecture", "MVP")
                ),
                Question(
                    id = "q4",
                    type = QuestionType.SLIDER,
                    question = "경력 연차를 선택해주세요.",
                    min = 0,
                    max = 10
                ),
                Question(
                    id = "q5",
                    type = QuestionType.LIKERT_SCALE,
                    question = "본인의 실력을 평가해 주세요.",
                    scaleList = listOf("매우 못함", "못함", "보통", "잘함", "매우 잘함"),
                ),
            )
        )


    }


}