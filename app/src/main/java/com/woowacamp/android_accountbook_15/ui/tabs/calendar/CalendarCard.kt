package com.woowacamp.android_accountbook_15.ui.tabs.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlin.math.ceil

@Composable
fun CalendarCard(
    modifier: Modifier = Modifier,
    days: List<Int>
) {
    val rowCount = ceil(days.size/7f).toInt()
    var i = 0

    val scrollState = rememberScrollState()
    Column(modifier = modifier.verticalScroll(scrollState)) {
        repeat(rowCount) {
            Row {
                repeat(7) {
                    CalendarItem()
                    i++
                    if (i >= days.size) return@Column
                }
            }
        }
    }
}

@Composable
fun CalendarItem(

) {

}