package com.example.composesurvey.view.components

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun PreviewSelectBoxList() {
    SelectBoxList(
        selectedList = listOf("MVVM", "MVI", "MVC", "Clean Architecture")
    )
}


@Composable
fun SelectBoxList(
    selectedList: List<String>,
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints {
        val isTablet = this.maxWidth > 600.dp

        if(isTablet) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 150.dp),
                modifier = modifier
            ) {
                items(selectedList) { item ->
                    SelectBox(
                        text = item,
                        value = false
                    )
                }
            }
        } else {
            Column(
                modifier = modifier
            ) {
                LazyColumn {
                    items(selectedList) { item ->
                        SelectBox(
                            text = item,
                            value = false
                        )
                    }
                }
            }
        }
    }
}