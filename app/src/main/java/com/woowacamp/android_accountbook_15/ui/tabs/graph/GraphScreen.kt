package com.woowacamp.android_accountbook_15.ui.tabs.graph

import androidx.activity.compose.BackHandler
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.woowacamp.android_accountbook_15.R
import com.woowacamp.android_accountbook_15.ui.components.DatePicker
import com.woowacamp.android_accountbook_15.ui.components.Header
import com.woowacamp.android_accountbook_15.ui.tabs.history.HistoryViewModel
import com.woowacamp.android_accountbook_15.utils.getMonthAndYearKorean

@Composable
fun GraphScreen(
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