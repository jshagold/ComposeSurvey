package com.example.composesurvey.mapper

import com.example.composesurvey.model.AnswerUI
import com.example.domain.model.Answer

fun AnswerUI.toDomain(): Answer {
    return when(this) {
        is AnswerUI.LikertScale -> Answer.LikertScale(selected = this.selected)
        is AnswerUI.MultipleChoice -> Answer.MultipleChoice(selected = this.selected)
        is AnswerUI.SingleChoice -> Answer.SingleChoice(selected = this.selected)
        is AnswerUI.Slider -> Answer.Slider(value = this.value)
        is AnswerUI.Text -> Answer.Text(value = this.value)
    }
}

fun Answer.toUI(): AnswerUI {
    return when(this) {
        is Answer.LikertScale -> AnswerUI.LikertScale(selected = this.selected)
        is Answer.MultipleChoice -> AnswerUI.MultipleChoice(selected = this.selected)
        is Answer.SingleChoice -> AnswerUI.SingleChoice(selected = this.selected)
        is Answer.Slider -> AnswerUI.Slider(value = this.value)
        is Answer.Text -> AnswerUI.Text(value = this.value)
    }
}