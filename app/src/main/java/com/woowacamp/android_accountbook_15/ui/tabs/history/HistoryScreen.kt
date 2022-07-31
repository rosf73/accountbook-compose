package com.woowacamp.android_accountbook_15.ui.tabs.history

import androidx.activity.compose.BackHandler
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
    val (modifyState, setModifyState) = remember { mutableStateOf(false) }
    val year by viewModel.currentYear.collectAsState()
    val month by viewModel.currentMonth.collectAsState()

    if (modifyState) {
        ModifyScreen {
            setModifyState(false)
        }
    } else {
        HistoryScreen(
            title = getMonthAndYearKorean(year, month),
            histories = viewModel.monthlyHistories.collectAsState().value,
            onChangeModifyState = { setModifyState(true) },
            onClickLeft = {
                viewModel.currentYear.value = if (month-1 > 0) year else year-1
                viewModel.currentMonth.value = if (month-1 > 0) month-1 else 12
            },
            onClickRight = {
                viewModel.currentYear.value = if (month+1 > 12) year+1 else year
                viewModel.currentMonth.value = if (month+1 > 12) 1 else month+1
            }
        )
    }
}

@Composable
private fun HistoryScreen(
    title: String,
    histories: Map<String, List<History>>,
    onChangeModifyState: () -> Unit,
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
            FloatingButton(onClick = onChangeModifyState)
        }
    ) {

    }
}

@Composable
private fun ModifyScreen(
    onChangeModifyState: () -> Unit
) {
    Scaffold(
        topBar = {
            Header(
                title = "내역 등록",
                leftIcon = painterResource(R.drawable.ic_back),
                leftCallback = onChangeModifyState
            )
        }
    ) {
        BackHandler {
            onChangeModifyState()
        }
    }
}