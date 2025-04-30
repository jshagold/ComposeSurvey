package com.example.core.serialize

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

// todo json key값 하드코딩

fun parseRequired(json: String?): Boolean {
    return runCatching {
        json?.let {
            Json.parseToJsonElement(it).jsonObject["required"]?.jsonPrimitive?.boolean == true
        } == true
    }.getOrDefault(false)
}

fun parseOptionsIfNeeded(type: String, json: String?): List<String>? {
    return if(type == "single_choice" || type == "multiple_choice") {
        runCatching {
            json?.let {
                Json.decodeFromString<List<String>>(it)
            }
        }.getOrNull()
    } else null
}

fun parseMinIfNeeded(type: String, json: String?): Int? {
    return if(type == "slider") {
        runCatching {
            json?.let {
                Json.parseToJsonElement(it).jsonObject["min"]?.jsonPrimitive?.int
            }
        }.getOrNull()
    } else null
}

fun parseMaxIfNeeded(type: String, json: String?): Int? {
    return if(type == "slider") {
        runCatching {
            json?.let {
                Json.parseToJsonElement(it).jsonObject["max"]?.jsonPrimitive?.int
            }
        }.getOrNull()
    } else null
}

fun parseScaleListIfNeeded(type: String, json: String?): List<String>? {
    return if(type == "likert_scale") {
        runCatching {
            json?.let {
                Json.decodeFromString<List<String>>(it)
            }
        }.getOrNull()
    } else null
}