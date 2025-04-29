package com.example.data.mapper

import com.example.data.model.Answer
import com.example.domain.model.Answer as AnswerDomain

fun Answer.toDomain(): AnswerDomain {
    return when(this) {
        is Answer.LikertScale -> AnswerDomain.LikertScale(selected = this.selected)
        is Answer.MultipleChoice -> AnswerDomain.MultipleChoice(selected = this.selected)
        is Answer.SingleChoice -> AnswerDomain.SingleChoice(selected = this.selected)
        is Answer.Slider -> AnswerDomain.Slider(value = this.value)
        is Answer.Text -> AnswerDomain.Text(value = this.value)
    }
}

fun AnswerDomain.toData(): Answer {
    return when(this) {
        is AnswerDomain.LikertScale -> Answer.LikertScale(selected = this.selected)
        is AnswerDomain.MultipleChoice -> Answer.MultipleChoice(selected = this.selected)
        is AnswerDomain.SingleChoice -> Answer.SingleChoice(selected = this.selected)
        is AnswerDomain.Slider -> Answer.Slider(value = this.value)
        is AnswerDomain.Text -> Answer.Text(value = this.value)
    }
}