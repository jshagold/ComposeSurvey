package com.example.composesurvey.view.converter

import com.example.composesurvey.data.model.Answer
import com.example.composesurvey.data.model.Question
import com.example.composesurvey.data.model.QuestionAndAnswer
import com.example.composesurvey.view.model.AnswerUI
import com.example.composesurvey.view.model.QuestionAndAnswerUI
import com.example.composesurvey.view.model.QuestionUI

fun Question.toQuestionUI(): QuestionUI {
    return QuestionUI(
        id = this.id,
        question = this.question,
        type = this.type,
        required = this.required,
        options = this.options,
        min = this.min,
        max = this.max,
        scaleList = this.scaleList
    )
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
        question = this.question,
        type = this.type,
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