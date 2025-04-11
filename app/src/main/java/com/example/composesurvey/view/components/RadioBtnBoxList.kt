package com.example.composesurvey.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.Collections.checkedList


@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun PreviewRadioBtnBoxList() {
    val list = listOf("Kotlin", "Java", "C++", "Python")
    val ans = "Kotlin"
    RadioBtnBoxList(
        selectedList = list,
        checkedItem = ans
    )
}


@Composable
fun RadioBtnBoxList(
    selectedList: List<String>,
    checkedItem: String,
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
                        value = checkedItem == item,
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
                        value = checkedItem == item,
                        onClick = { onClickCheckBox(item) }
                    )
                }
            }
        }
    }
}