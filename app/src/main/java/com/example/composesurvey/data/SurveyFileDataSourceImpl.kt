package com.example.composesurvey.data

import android.content.Context
import com.example.composesurvey.data.exception.FileException
import com.example.composesurvey.data.exception.UnexpectedException
import com.example.composesurvey.model.Survey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.IOException
import javax.inject.Inject

class SurveyFileDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SurveyFileDataSource {

    private val assetFolder = "survey"

    @OptIn(ExperimentalSerializationApi::class)
    override fun getSurvey(fileName: String): Survey? {
        try {
            val assetManager = context.resources.assets
            val jsonFile = assetManager.open("$assetFolder/$fileName")
            val survey = Json.decodeFromStream<Survey>(jsonFile)

            return survey
        } catch (e: IOException) {
            throw FileException("파일 에러", e)
        } catch (e: SerializationException) {
            throw FileException("Json decode 에러", e)
        } catch (e: IllegalArgumentException) {
            throw FileException("Json decode 에러", e)
        } catch (e: Exception) {
            throw UnexpectedException("원인 불명", e)
        }

        return null
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun getSurveyTitleList(): List<String> {
        val titleList = mutableListOf<String>()

        try {
            val assetManager = context.resources.assets
            val fileList = assetManager.list("survey")

            fileList?.let {
                it.forEach {
                    val fileStream = assetManager.open("$assetFolder/$it")
                    val jsonFile = Json.decodeFromStream<Survey>(fileStream)

                    titleList.add(jsonFile.title)
                }
            }
        } catch (e: IOException) {
            throw FileException("파일 에러", e)
        } catch (e: SerializationException) {
            throw FileException("Json decode 에러", e)
        } catch (e: IllegalArgumentException) {
            throw FileException("Json decode 에러", e)
        } catch (e: Exception) {
            throw UnexpectedException("원인 불명", e)
        }

        return titleList
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun getSurveyList(): List<Survey> {
        val assetFolder = "survey"

        val surveyList = mutableListOf<Survey>()

        try {
            val assetManager = context.resources.assets

            val fileList = assetManager.list("survey")

            fileList?.let {
                it.forEach {
                    val fileStream = assetManager.open("$assetFolder/$it")
                    surveyList.add(Json.decodeFromStream<Survey>(fileStream))
                }
            }

        } catch (e: IOException) {
            throw FileException("파일 에러", e)
        } catch (e: SerializationException) {
            throw FileException("Json decode 에러", e)
        } catch (e: IllegalArgumentException) {
            throw FileException("Json decode 에러", e)
        } catch (e: Exception) {
            throw UnexpectedException("원인 불명", e)
        }

        return surveyList
    }
}





