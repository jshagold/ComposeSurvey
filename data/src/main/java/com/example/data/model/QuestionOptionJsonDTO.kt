package com.example.data.model

import kotlinx.serialization.Serializable

@Serializable
data class QuestionOptionJsonDTO(
    val required: Boolean = false,
    val options: List<String>? = null,
    val min: Int? = null,
    val max: Int? = null,
    val scaleList: List<String>? = null
)