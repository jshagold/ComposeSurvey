package com.example.composesurvey.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.composesurvey.view.state.SurveyListState
import com.example.composesurvey.viewmodel.SurveyListViewModel


@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun PreviewSurveyListScreen() {
    SurveyListScreen(
        uiState = SurveyListState()
    )
}


@Composable
fun SurveyListRoute(
    modifier: Modifier = Modifier,
    viewModel: SurveyListViewModel = hiltViewModel()
) {

    val uiState by viewModel.surveyListState.collectAsStateWithLifecycle()

    SurveyListScreen(
        uiState = uiState,
        modifier = modifier
    )
}


@Composable
fun SurveyListScreen(
    modifier: Modifier = Modifier,
    uiState: SurveyListState
) {

    LazyColumn(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .background(White)
            .padding(horizontal = 10.dp)
    ) {
        items(uiState.titleList) { title ->
            Text(
                text = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Black, RoundedCornerShape(10.dp))
            )
        }
    }
}