package com.woowacamp.android_accountbook_15.ui.tabs.history

import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import com.woowacamp.android_accountbook_15.R
import com.woowacamp.android_accountbook_15.data.model.History
import com.woowacamp.android_accountbook_15.ui.components.FloatingButton
import com.woowacamp.android_accountbook_15.ui.components.Header
import com.woowacamp.android_accountbook_15.utils.getMonthAndYearKorean

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel
) {
    val (screenState, setScreenState) = remember { mutableStateOf(ScreenType.HISTORY) }
    val (isCheckedIncome, setIsCheckedIncome) = remember { mutableStateOf(true) }
    val (isCheckedExpenses, setIsCheckedExpenses) = remember { mutableStateOf(true) }

    val year by viewModel.currentYear.collectAsState()
    val month by viewModel.currentMonth.collectAsState()

    when (screenState) {
        ScreenType.HISTORY -> HistoryScreen(
            title = getMonthAndYearKorean(year, month),
            histories = viewModel.monthlyHistories.collectAsState().value,
            onAddClick = { setScreenState(ScreenType.ADD_HISTORY) },
            onClickLeft = {
                viewModel.setCurrentDate(
                    if (month-1 > 0) year else year-1,
                    if (month-1 > 0) month-1 else 12
                )
            },
            onClickRight = {
                viewModel.setCurrentDate(
                    if (month+1 > 12) year+1 else year,
                    if (month+1 > 12) 1 else month+1
                )
            }
        )
        ScreenType.ADD_HISTORY -> EditScreen(
            isCheckedIncome = isCheckedIncome,
            onAddClick = {  },
            onBackClick = { setScreenState(ScreenType.HISTORY) }
        )
        ScreenType.UPDATE_HISTORY -> EditScreen(
            isCheckedIncome = isCheckedIncome,
            onAddClick = {  },
            onBackClick = { setScreenState(ScreenType.HISTORY) }
        )
    }
}

@Composable
private fun HistoryScreen(
    title: String,
    histories: Map<String, List<History>>,
    onAddClick: () -> Unit,
    onClickLeft: () -> Unit,
    onClickRight: () -> Unit
) {
    Scaffold(
        topBar = {
            Header(
                title = title,
                leftIcon = painterResource(R.drawable.ic_left),
                leftCallback = onClickLeft,
                rightIcon = painterResource(R.drawable.ic_right),
                rightCallback = onClickRight
            )
        },
        floatingActionButton = {
            FloatingButton(onClick = onAddClick)
        }
    ) {

    }
}

enum class ScreenType {
    HISTORY, ADD_HISTORY, UPDATE_HISTORY
}