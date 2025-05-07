package com.example.composesurvey.view.converter

import com.example.domain.model.Answer
import com.example.domain.model.Question
import com.example.domain.model.QuestionAndAnswer
import com.example.composesurvey.model.AnswerUI
import com.example.composesurvey.model.QuestionAndAnswerUI
import com.example.composesurvey.model.QuestionTypeUI
import com.example.composesurvey.model.QuestionUI
import com.example.domain.model.QuestionType

fun Question.toQuestionUI(): QuestionUI {
    return QuestionUI(
        id = this.id,
        surveyId = this.surveyId,
        question = this.question,
        type = this.type.toQuestionTypeUI(),
        required = this.required,
        options = this.options,
        min = this.min,
        max = this.max,
        scaleList = this.scaleList
    )
}

fun QuestionType.toQuestionTypeUI(): QuestionTypeUI {
    return when(this) {
        QuestionType.TEXT -> QuestionTypeUI.TEXT
        QuestionType.SINGLE_CHOICE -> QuestionTypeUI.SINGLE_CHOICE
        QuestionType.MULTIPLE_CHOICE -> QuestionTypeUI.MULTIPLE_CHOICE
        QuestionType.SLIDER -> QuestionTypeUI.SLIDER
        QuestionType.LIKERT_SCALE -> QuestionTypeUI.LIKERT_SCALE
    }
}

fun QuestionTypeUI.toQuestionType(): QuestionType {
    return when(this) {
        QuestionTypeUI.TEXT -> QuestionType.TEXT
        QuestionTypeUI.SINGLE_CHOICE -> QuestionType.SINGLE_CHOICE
        QuestionTypeUI.MULTIPLE_CHOICE -> QuestionType.MULTIPLE_CHOICE
        QuestionTypeUI.SLIDER -> QuestionType.SLIDER
        QuestionTypeUI.LIKERT_SCALE -> QuestionType.LIKERT_SCALE
    }
}

fun Answer.toAnswerUI(): AnswerUI {
    return when (val answer = this) {
        is Answer.LikertScale -> AnswerUI.LikertScale(answer.selected)
        is Answer.MultipleChoice -> AnswerUI.MultipleChoice(answer.selected)
        is Answer.SingleChoice -> AnswerUI.SingleChoice(answer.selected)
        is Answer.Slider -> AnswerUI.Slider(answer.value)
        is Answer.Text -> AnswerUI.Text(answer.value)
    }
}

fun QuestionAndAnswer.toQuestionAndAnswerUI(): QuestionAndAnswerUI {
    return QuestionAndAnswerUI(
        question = this.question.toQuestionUI(),
        answer = this.answer.toAnswerUI()
    )
}

fun QuestionUI.toQuestion(): Question {
    return Question(
        id = this.id,
        surveyId = this.surveyId,
        question = this.question,
        type = this.type.toQuestionType(),
        required = this.required,
        options = this.options,
        min = this.min,
        max = this.max,
        scaleList = this.scaleList
    )
}

fun AnswerUI.toAnswer(): Answer {
    return when (val answer = this) {
        is AnswerUI.LikertScale -> Answer.LikertScale(answer.selected)
        is AnswerUI.MultipleChoice -> Answer.MultipleChoice(answer.selected)
        is AnswerUI.SingleChoice -> Answer.SingleChoice(answer.selected)
        is AnswerUI.Slider -> Answer.Slider(answer.value)
        is AnswerUI.Text -> Answer.Text(answer.value)
    }
}

fun QuestionAndAnswerUI.toQuestionAndAnswer(): QuestionAndAnswer {
    return QuestionAndAnswer(
        question = this.question.toQuestion(),
        answer = this.answer.toAnswer()
    )
}