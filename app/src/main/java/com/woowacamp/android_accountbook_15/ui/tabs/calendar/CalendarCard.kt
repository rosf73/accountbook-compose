package com.woowacamp.android_accountbook_15.ui.tabs.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.woowacamp.android_accountbook_15.data.model.History
import com.woowacamp.android_accountbook_15.ui.theme.*
import com.woowacamp.android_accountbook_15.utils.getDaysOfMonth
import com.woowacamp.android_accountbook_15.utils.getMonthAndDateKorean
import com.woowacamp.android_accountbook_15.utils.isToday
import com.woowacamp.android_accountbook_15.utils.toMoneyString
import kotlin.math.ceil

@Composable
fun CalendarCard(
    modifier: Modifier = Modifier,
    year: Int,
    month: Int,
    histories: Map<String, List<History>>
) {
    val days = getDaysOfMonth(year, month)

    val rowCount = ceil(days.size/7f).toInt()
    var i = 0
    var curM = month-1

    val scrollState = rememberScrollState()
    Column(modifier = modifier.verticalScroll(scrollState)) {
        DayCard()
        Divider(color = LightPurple, thickness = 1.dp)
        repeat(rowCount) {
            Row {
                for (j in 0 until 7) {
                    if (days[i] == 1) curM++ // 달력 하나에 두 달 이상이 겹쳐 있으므로 구분하여 today 체크

                    val current = getMonthAndDateKorean(curM, days[i])
                    val totalIncome =
                        if (histories.containsKey(current))
                            histories[current]!!.sumOf { if (it.type == 1) it.amount else 0 }
                        else
                            0
                    val totalExpenses =
                        if (histories.containsKey(current))
                            histories[current]!!.sumOf { if (it.type == 0) it.amount else 0 }
                        else
                            0
                    CalendarItem(
                        modifier = Modifier
                            .weight(1f)
                            .height(80.dp),
                        isToday = isToday(year, curM, days[i]),
                        isTodayMonth = curM == month,
                        date = days[i],
                        totalIncome, totalExpenses)

                    if ((i+1)%7>0)
                        Divider(color = LightPurple, modifier = Modifier
                            .height(80.dp)
                            .width(1.dp))

                    i++
                    if (i >= days.size) break
                }
            }
            Divider(color = LightPurple, thickness = 1.dp)
        }
    }
}

@Composable
fun DayCard(
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.height(IntrinsicSize.Min)) {
        val textModifier = Modifier
            .weight(1f)
            .padding(4.dp)
        Text(modifier = textModifier, text = "일", textAlign = TextAlign.Center, fontSize = 12.sp, color = Red)
        Divider(color = LightPurple, modifier = Modifier.fillMaxHeight().width(1.dp))
        Text(modifier = textModifier, text = "월", textAlign = TextAlign.Center, fontSize = 12.sp)
        Divider(color = LightPurple, modifier = Modifier.fillMaxHeight().width(1.dp))
        Text(modifier = textModifier, text = "화", textAlign = TextAlign.Center, fontSize = 12.sp)
        Divider(color = LightPurple, modifier = Modifier.fillMaxHeight().width(1.dp))
        Text(modifier = textModifier, text = "수", textAlign = TextAlign.Center, fontSize = 12.sp)
        Divider(color = LightPurple, modifier = Modifier.fillMaxHeight().width(1.dp))
        Text(modifier = textModifier, text = "목", textAlign = TextAlign.Center, fontSize = 12.sp)
        Divider(color = LightPurple, modifier = Modifier.fillMaxHeight().width(1.dp))
        Text(modifier = textModifier, text = "금", textAlign = TextAlign.Center, fontSize = 12.sp)
        Divider(color = LightPurple, modifier = Modifier.fillMaxHeight().width(1.dp))
        Text(modifier = textModifier, text = "토", textAlign = TextAlign.Center, fontSize = 12.sp, color = Blue)
    }
}

@Composable
fun CalendarItem(
    modifier: Modifier = Modifier,
    isToday: Boolean,
    isTodayMonth: Boolean,
    date: Int,
    totalIncome: Int,
    totalExpenses: Int
) {
    Box(
        modifier = modifier
            .background(if (isToday) White else OffWhite)
            .padding(4.dp)
    ) {
        Column(modifier = Modifier.align(Alignment.TopStart)) {
            if (totalIncome != 0) Text(text = totalIncome.toMoneyString(), fontSize = 8.sp, color = Blue)
            if (totalExpenses != 0) Text(text = "-${totalExpenses.toMoneyString()}", fontSize = 8.sp, color = Red)
            if (totalIncome != 0 || totalExpenses != 0) Text(text = (totalIncome-totalExpenses).toMoneyString(), fontSize = 8.sp)
        }
        Text(
            modifier = Modifier.align(Alignment.BottomEnd),
            text = date.toString(),
            fontSize = 8.sp,
            color = if (isTodayMonth) Purple else LightPurple)
    }
}

@Preview
@Composable
fun TestDays() {
    DayCard()
}