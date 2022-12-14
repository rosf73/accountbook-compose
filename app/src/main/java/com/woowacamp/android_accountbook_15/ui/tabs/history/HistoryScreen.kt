package com.woowacamp.android_accountbook_15.ui.tabs.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.woowacamp.android_accountbook_15.R
import com.woowacamp.android_accountbook_15.data.model.History
import com.woowacamp.android_accountbook_15.ui.components.*
import com.woowacamp.android_accountbook_15.ui.tabs.setting.SettingViewModel
import com.woowacamp.android_accountbook_15.ui.theme.Grey1
import com.woowacamp.android_accountbook_15.ui.theme.LightPurple
import com.woowacamp.android_accountbook_15.ui.theme.Purple
import com.woowacamp.android_accountbook_15.ui.theme.White
import com.woowacamp.android_accountbook_15.utils.getDayKoreanWithoutYear
import com.woowacamp.android_accountbook_15.utils.getMonthAndYearKorean
import com.woowacamp.android_accountbook_15.utils.toMoneyString

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel(),
    settingViewModel: SettingViewModel = hiltViewModel()
) {
    val (screenState, setScreenState) = remember { mutableStateOf(ScreenType.HISTORY) }

    val (isCheckedIncome, setIsCheckedIncome) = remember { mutableStateOf(true) }
    val (isCheckedExpenses, setIsCheckedExpenses) = remember { mutableStateOf(true) }

    val (isDateOpened, setDateOpened) = remember { mutableStateOf(false) }

    when (screenState) {
        ScreenType.HISTORY -> HistoryScreen(
            viewModel,
            isSelectedIncome = isCheckedIncome,
            isSelectedExpenses = isCheckedExpenses,
            onIncomeClick = setIsCheckedIncome,
            onExpensesClick = setIsCheckedExpenses,
            isDateOpened = isDateOpened,
            setDateOpened = setDateOpened,
            onAddClick = { setScreenState(ScreenType.ADD_HISTORY) },
            onUpdateClick = { history ->
                viewModel.history.value = history
                setScreenState(ScreenType.UPDATE_HISTORY)
            }
        )
        ScreenType.ADD_HISTORY -> EditScreen(
            title = "?????? ??????",
            settingViewModel,
            isCheckedIncome = isCheckedIncome,
            onAddClick = {
                viewModel.insertHistory(it)
                setScreenState(ScreenType.HISTORY)
            },
            onBackClick = { setScreenState(ScreenType.HISTORY) }
        )
        ScreenType.UPDATE_HISTORY -> EditScreen(
            title = "?????? ??????",
            settingViewModel,
            isCheckedIncome = isCheckedIncome,
            history = viewModel.history.collectAsState().value,
            onAddClick = {
                viewModel.updateHistory(it)
                setScreenState(ScreenType.HISTORY)
            },
            onBackClick = { setScreenState(ScreenType.HISTORY) }
        )
    }
}

@Composable
private fun HistoryScreen(
    viewModel: HistoryViewModel,
    isSelectedIncome: Boolean,
    isSelectedExpenses: Boolean,
    onAddClick: () -> Unit,
    onIncomeClick: (Boolean) -> Unit,
    onExpensesClick: (Boolean) -> Unit,
    isDateOpened: Boolean,
    setDateOpened: (Boolean) -> Unit,
    onUpdateClick: (History) -> Unit
) {
    val year by viewModel.currentYear.collectAsState()
    val month by viewModel.currentMonth.collectAsState()

    val histories by viewModel.monthlyHistories.collectAsState()
    val selectedHistories = viewModel.selectedHistories

    val (selectMode, setSelectMode) = remember { mutableStateOf(false) }

    val totalIncome = histories.values.sumOf { it.sumOf { history -> if (history.type == 1) history.amount else 0 } }
    val totalExpenses = histories.values.sumOf { it.sumOf { history -> if (history.type == 0) history.amount else 0 } }

    Scaffold(
        topBar = {
            if (!selectMode)
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
            else
                Header(
                    title = "${selectedHistories.size}??? ?????????",
                    leftIcon = painterResource(R.drawable.ic_back),
                    onLeftClick = {
                        setSelectMode(false)
                        viewModel.removeAllSelectedHistories()
                    },
                    rightIcon = painterResource(R.drawable.ic_trash),
                    onRightClick = {
                        viewModel.removeHistories()
                        setSelectMode(false)
                    })
        },
        floatingActionButton = {
            FloatingButton(onClick = onAddClick)
        }
    ) {
        Column {
            TypeCheckBoxGroup(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                selectMode = selectMode,
                totalIncome = totalIncome,
                totalExpenses = totalExpenses,
                isSelectedIncome = isSelectedIncome,
                isSelectedExpenses = isSelectedExpenses,
                onIncomeClick = onIncomeClick,
                onExpensesClick = onExpensesClick)

            Spacer(modifier = Modifier.size(16.dp))

            if (histories.isNotEmpty() &&
                    ((isSelectedIncome && histories.filterValues { list -> list.any { it.type == 1 } }.isNotEmpty()) || // ????????? ???????????? ???????????? ?????? ????????? 1??? ???????
                    (isSelectedExpenses && histories.filterValues { list -> list.any { it.type == 0 } }.isNotEmpty()))) // ????????? ???????????? ???????????? ?????? ????????? 1??? ???????
                LazyColumn {
                    item {
                            histories.forEach { (key, value) ->
                                val splitDate = key.split("-").map { it.toInt() }
                                HistoryCard(
                                    isSelectedIncome = isSelectedIncome,
                                    isSelectedExpenses = isSelectedExpenses,
                                    date = getDayKoreanWithoutYear(year, splitDate[0], splitDate[1]),
                                    list = value,
                                    selectMode = selectMode,
                                    onPress = {
                                        viewModel.deselectHistory(it)
                                        if (selectedHistories.isEmpty()) setSelectMode(false)
                                    },
                                    onLongPress = {
                                        setSelectMode(true)
                                        viewModel.selectHistory(it)
                                    },
                                    onUpdateClick = onUpdateClick)
                            }
                        Spacer(modifier = Modifier.size(88.dp))
                    }
                }
            else
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Center
                ) {
                    Text(modifier = Modifier.fillMaxWidth(), text = "????????? ????????????", fontSize = 12.sp, color = Grey1, textAlign = TextAlign.Center)
                }
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

@Composable
private fun TypeCheckBoxGroup(
    modifier: Modifier,
    selectMode: Boolean,
    totalIncome: Int,
    totalExpenses: Int,
    isSelectedIncome: Boolean,
    isSelectedExpenses: Boolean,
    onIncomeClick: (Boolean) -> Unit,
    onExpensesClick: (Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .background(if (isSelectedIncome && !selectMode) Purple else LightPurple)
                .padding(8.dp)
                .clickable {
                    if (!selectMode)
                        onIncomeClick(!isSelectedIncome)
                },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PurpleCheckBox(checked = isSelectedIncome, onCheckedChange = onIncomeClick)
            Text(
                modifier = Modifier.padding(4.dp, 0.dp),
                text = "?????? ${totalIncome.toMoneyString()}",
                textAlign = TextAlign.Center,
                color = White,
                fontSize = 12.sp)
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .background(if (isSelectedExpenses && !selectMode) Purple else LightPurple)
                .padding(8.dp)
                .clickable {
                    if (!selectMode)
                        onExpensesClick(!isSelectedExpenses)
                },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PurpleCheckBox(checked = isSelectedExpenses, onCheckedChange = onExpensesClick)
            val amount = if (totalExpenses != 0) "-${totalExpenses.toMoneyString()}" else "0"
            Text(
                modifier = Modifier.padding(4.dp, 0.dp),
                text = "?????? $amount",
                textAlign = TextAlign.Center,
                color = White,
                fontSize = 12.sp)
        }
    }
}

enum class ScreenType {
    HISTORY, ADD_HISTORY, UPDATE_HISTORY
}