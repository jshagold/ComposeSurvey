package com.example.data.datasource

import android.content.Context
import android.util.Log
import com.example.core.result.Result
import com.example.core.exception.FileException
import com.example.core.exception.UnexpectedException
import com.example.data.mapper.toDomain
import com.example.data.model.Survey
import com.example.domain.model.Survey as SurveyDomain
import com.example.domain.model.SurveyPreview
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
    override fun getSurvey(fileName: String): Survey {
        try {
            val assetManager = context.resources.assets
            val jsonFile = assetManager.open("$assetFolder/$fileName")
            val survey = Json.Default.decodeFromStream<Survey>(jsonFile)

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
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun getSurveyTitleList(): List<Result<SurveyPreview>> {
        try {
            val assetManager = context.resources.assets
            val fileList = assetManager.list("survey") ?: return emptyList()

            return fileList.map { fileName ->
                try {
                    val fileStream = assetManager.open("$assetFolder/$fileName")
                    val jsonFile = Json.Default.decodeFromStream<Survey>(fileStream)
                    Result.Success(SurveyPreview(title = jsonFile.title, fileName = fileName))
                } catch (e: IOException) {
                    Log.e("TAG", "getSurveyTitleList: ${e.printStackTrace()}", )
                    Result.Failure(
                        message = "fileName = $fileName",
                        cause = FileException("파일 에러", e)
                    )
                } catch (e: SerializationException) {
                    Result.Failure(
                        message = "fileName = $fileName",
                        cause = FileException("Json decode 에러", e)
                    )
                } catch (e: IllegalArgumentException) {
                    Result.Failure(
                        message = "fileName = $fileName",
                        cause = FileException("Json decode 에러", e)
                    )
                } catch (e: Exception) {
                    Result.Failure(
                        message = "fileName = $fileName",
                        cause = UnexpectedException("Error", e)
                    )
                }
            }
        } catch (e: IOException) {
            Log.e("TAG", "getSurveyTitleList: ${e.printStackTrace()}", )
            throw FileException("파일 에러", e)
        } catch (e: Exception) {
            throw UnexpectedException("Error", e)
        }
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
                    surveyList.add(Json.Default.decodeFromStream<Survey>(fileStream))
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