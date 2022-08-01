package com.woowacamp.android_accountbook_15.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.chargemap.compose.numberpicker.NumberPicker
import com.woowacamp.android_accountbook_15.utils.getDayKorean
import com.woowacamp.android_accountbook_15.utils.getTodayYear


@Composable
fun DatePicker(
    modifier: Modifier = Modifier,
    initYear: Int,
    initMonth: Int,
    initDate: Int? = null,
    onOpen: (Boolean) -> Unit,
    onTextChanged: (String) -> Unit
) {
    var year by remember { mutableStateOf(initYear) }
    var month by remember { mutableStateOf(initMonth) }
    var date by remember { mutableStateOf(initDate ?: 0) }

    Dialog(onDismissRequest = { onOpen(false) }) {
        Column(modifier = modifier) {
            Row {
                NumberPicker(
                    Modifier.weight(1f).padding(8.dp),
                    value = year, onValueChange = { year = it }, range = 2000..getTodayYear())
                NumberPicker(
                    Modifier.weight(1f).padding(8.dp),
                    value = month, onValueChange = { month = it }, range = 1..12)
                initDate?.let {
                    NumberPicker(
                        Modifier.weight(1f).padding(8.dp),
                        value = date, onValueChange = { selectedDate -> date = selectedDate }, range = 1..31)
                }
            }
            Button(
                modifier = Modifier.align(CenterHorizontally).width(200.dp),
                onClick = {
                    onTextChanged(getDayKorean(year, month, date))
                    onOpen(false)
                }
            ) {
                Text(text = "확인")
            }
        }
    }
}

@Preview
@Composable
private fun DialogView() {
    DatePicker(
        initYear = 2022,
        initMonth = 7,
        initDate = 26,
        onOpen = {},
        onTextChanged = {})
}