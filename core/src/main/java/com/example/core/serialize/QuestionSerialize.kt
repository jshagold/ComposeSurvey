package com.example.core.serialize

import com.example.core.constants.SurveyJson
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

fun parseRequired(json: String?): Boolean {
    return runCatching {
        json?.let {
            Json.parseToJsonElement(it).jsonObject[SurveyJson.REQUIRED]?.jsonPrimitive?.boolean == true
        } == true
    }.getOrDefault(false)
}

fun parseOptionsIfNeeded(type: String, json: String?): List<String>? {
    return if(type == SurveyJson.Type.SINGLE_CHOICE || type == SurveyJson.Type.MULTIPLE_CHOICE) {
        runCatching {
            json?.let {
                val option = Json.parseToJsonElement(it).jsonObject[SurveyJson.OPTIONS]
                Json.decodeFromJsonElement<List<String>>(option!!)
            }
        }.getOrNull()
    } else null
}

fun parseMinIfNeeded(type: String, json: String?): Int? {
    return if(type == SurveyJson.Type.SLIDER) {
        runCatching {
            json?.let {
                Json.parseToJsonElement(it).jsonObject[SurveyJson.MIN]?.jsonPrimitive?.int
            }
        }.getOrNull()
    } else null
}

fun parseMaxIfNeeded(type: String, json: String?): Int? {
    return if(type == SurveyJson.Type.SLIDER) {
        runCatching {
            json?.let {
                Json.parseToJsonElement(it).jsonObject[SurveyJson.MAX]?.jsonPrimitive?.int
            }
        }.getOrNull()
    } else null
}

fun parseScaleListIfNeeded(type: String, json: String?): List<String>? {
    return if(type == SurveyJson.Type.LIKERT_SCALE) {
        runCatching {
            json?.let {
                val scaleList = Json.parseToJsonElement(it).jsonObject[SurveyJson.SCALE_LIST]
                Json.decodeFromJsonElement<List<String>>(scaleList!!)
            }
        }.getOrNull()
    } else null
}