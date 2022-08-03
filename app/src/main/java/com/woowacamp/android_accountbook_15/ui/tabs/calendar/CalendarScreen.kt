package com.woowacamp.android_accountbook_15.ui.tabs.calendar

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.woowacamp.android_accountbook_15.R
import com.woowacamp.android_accountbook_15.ui.components.DatePicker
import com.woowacamp.android_accountbook_15.ui.components.Header
import com.woowacamp.android_accountbook_15.ui.tabs.history.HistoryViewModel
import com.woowacamp.android_accountbook_15.ui.theme.Blue
import com.woowacamp.android_accountbook_15.ui.theme.LightPurple
import com.woowacamp.android_accountbook_15.ui.theme.Purple
import com.woowacamp.android_accountbook_15.ui.theme.Red
import com.woowacamp.android_accountbook_15.utils.getMonthAndYearKorean

@Composable
fun CalendarScreen(
    viewModel: HistoryViewModel = hiltViewModel(),
    backToMain: () -> Unit
) {
    val year by viewModel.currentYear.collectAsState()
    val month by viewModel.currentMonth.collectAsState()

    val (isDateOpened, setDateOpened) = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Header(
                title = getMonthAndYearKorean(year, month),
                onTitleClick = { setDateOpened(true) },
                leftIcon = painterResource(R.drawable.ic_left),
                onLeftClick = {
                    viewModel.setCurrentDate(
                        if (month-1 > 0) year else year-1,
                        if (month-1 > 0) month-1 else 12
                    )
                },
                rightIcon = painterResource(R.drawable.ic_right),
                onRightClick = {
                    viewModel.setCurrentDate(
                        if (month+1 > 12) year+1 else year,
                        if (month+1 > 12) 1 else month+1
                    )
                })
        }
    ) {
        BackHandler {
            backToMain()
        }

        Column {
            val histories by viewModel.monthlyHistories.collectAsState()
            CalendarCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                year = year,
                month = month,
                histories = histories)

            val totalIncome = histories.values.sumOf { list -> list.sumOf { if (it.type == 1) it.amount else 0 } }
            val totalExpenses = histories.values.sumOf { list -> list.sumOf { if (it.type == 0) it.amount else 0 } }
            StatisticsItem(label = "수입", amountColor = Blue, amount = totalIncome)
            Divider(modifier = Modifier.padding(16.dp, 0.dp), color = LightPurple, thickness = 1.dp)
            StatisticsItem(label = "지출", amountColor = Red, amount = totalExpenses*-1)
            Divider(modifier = Modifier.padding(16.dp, 0.dp), color = LightPurple, thickness = 1.dp)
            StatisticsItem(label = "총합", amountColor = Purple, amount = totalIncome-totalExpenses)
        }
    }

    if (isDateOpened) {
        DatePicker(
            initYear = year,
            initMonth = month,
            onOpen = setDateOpened,
            onDateChanged = { newY, newM, _ ->
                viewModel.setCurrentDate(newY, newM)
            })
    }
}