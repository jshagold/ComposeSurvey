package com.example.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.mapper.toData
import com.example.data.model.Survey
import com.example.database.dao.SurveyDao
import javax.inject.Inject

class SurveyPagingSource @Inject constructor(
    private val surveyDao: SurveyDao,
) : PagingSource<Int, Survey>() {
    override fun getRefreshKey(state: PagingState<Int, Survey>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Survey> {
        return try {
            val page = params.key ?: 1
            val surveyList: List<Survey> = surveyDao.getSurveyWithQuestionsListByPage(page, params.loadSize).map { surveyWithQuestions ->
                Survey(
                    id = surveyWithQuestions.survey.id,
                    title = surveyWithQuestions.survey.title,
                    description = surveyWithQuestions.survey.description,
                    questions = surveyWithQuestions.questions.map { it.toData() }
                )
            }

            LoadResult.Page(
                data = surveyList,
                prevKey = if(page == 1) null else page - 1,
                nextKey = if(surveyList.isEmpty()) null else page + 1,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}