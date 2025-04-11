package com.example.composesurvey.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun PreviewSelectBoxList() {
    val list = listOf("MVVM", "MVI", "MVC", "Clean Architecture")
    val ans = listOf("MVVM", "MVC")
    SelectBoxList(
        selectedList = list,
        checkedList = ans
    )
}


@Composable
fun SelectBoxList(
    selectedList: List<String>,
    checkedList: List<String>,
    onClickCheckBox: (key: String) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(
        modifier = modifier
    ) {
        val isTablet = this.maxWidth > 600.dp

        if(isTablet) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                selectedList.forEachIndexed { index, item ->
                    SelectBox(
                        text = item,
                        value = checkedList.contains(item),
                        onClick = { onClickCheckBox(item) },
                        modifier = Modifier
                            .weight(1f)
                    )
                }
            }

        } else {
            Column(
                modifier = Modifier
            ) {
                selectedList.forEachIndexed { index, item ->
                    if(index != 0) {
                        Spacer(
                            modifier = Modifier
                                .height(10.dp)
                        )
                    }
                    SelectBox(
                        text = item,
                        value = checkedList.contains(item),
                        onClick = { onClickCheckBox(item) }
                    )
                }
            }
        }
    }
}