package com.woowacamp.android_accountbook_15.ui.tabs.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.woowacamp.android_accountbook_15.R
import com.woowacamp.android_accountbook_15.data.model.History
import com.woowacamp.android_accountbook_15.ui.components.FloatingButton
import com.woowacamp.android_accountbook_15.ui.components.Header
import com.woowacamp.android_accountbook_15.ui.components.PurpleCheckBox
import com.woowacamp.android_accountbook_15.ui.tabs.setting.SettingViewModel
import com.woowacamp.android_accountbook_15.ui.theme.LightPurple
import com.woowacamp.android_accountbook_15.ui.theme.Purple
import com.woowacamp.android_accountbook_15.ui.theme.White
import com.woowacamp.android_accountbook_15.utils.getDayKoreanWithoutYear
import com.woowacamp.android_accountbook_15.utils.getMonthAndYearKorean

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel(),
    settingViewModel: SettingViewModel = hiltViewModel()
) {
    val (screenState, setScreenState) = remember { mutableStateOf(ScreenType.HISTORY) }
    val (isCheckedIncome, setIsCheckedIncome) = remember { mutableStateOf(true) }
    val (isCheckedExpenses, setIsCheckedExpenses) = remember { mutableStateOf(true) }

    val year by viewModel.currentYear.collectAsState()
    val month by viewModel.currentMonth.collectAsState()

    when (screenState) {
        ScreenType.HISTORY -> HistoryScreen(
            year,
            title = getMonthAndYearKorean(year, month),
            histories = viewModel.monthlyHistories.collectAsState().value,
            isSelectedIncome = isCheckedIncome,
            isSelectedExpenses = isCheckedExpenses,
            onAddClick = { setScreenState(ScreenType.ADD_HISTORY) },
            onLeftClick = {
                viewModel.setCurrentDate(
                    if (month-1 > 0) year else year-1,
                    if (month-1 > 0) month-1 else 12
                )
            },
            onRightClick = {
                viewModel.setCurrentDate(
                    if (month+1 > 12) year+1 else year,
                    if (month+1 > 12) 1 else month+1
                )
            },
            onIncomeClick = setIsCheckedIncome,
            onExpensesClick = setIsCheckedExpenses
        )
        ScreenType.ADD_HISTORY -> EditScreen(
            settingViewModel,
            isCheckedIncome = isCheckedIncome,
            onAddClick = {
                viewModel.insertHistory(it)
                setScreenState(ScreenType.HISTORY)
            },
            onBackClick = { setScreenState(ScreenType.HISTORY) }
        )
        ScreenType.UPDATE_HISTORY -> EditScreen(
            settingViewModel,
            isCheckedIncome = isCheckedIncome,
            onAddClick = {
                viewModel.insertHistory(it)
                setScreenState(ScreenType.HISTORY)
            },
            onBackClick = { setScreenState(ScreenType.HISTORY) }
        )
    }
}

@Composable
private fun HistoryScreen(
    year: Int,
    title: String,
    histories: Map<String, List<History>>,
    isSelectedIncome: Boolean,
    isSelectedExpenses: Boolean,
    onAddClick: () -> Unit,
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit,
    onIncomeClick: (Boolean) -> Unit,
    onExpensesClick: (Boolean) -> Unit
) {
    Scaffold(
        topBar = {
            Header(
                title = title,
                leftIcon = painterResource(R.drawable.ic_left),
                leftCallback = onLeftClick,
                rightIcon = painterResource(R.drawable.ic_right),
                rightCallback = onRightClick)
        },
        floatingActionButton = {
            FloatingButton(onClick = onAddClick)
        }
    ) {
        Column {
            TypeCheckBoxGroup(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                totalIncome = 0,
                totalExpenses = 0,
                isSelectedIncome = isSelectedIncome,
                isSelectedExpenses = isSelectedExpenses,
                onIncomeClick = onIncomeClick,
                onExpensesClick = onExpensesClick)

            Spacer(modifier = Modifier.size(16.dp))

            LazyColumn {
                item {
                    histories.forEach { entry ->
                        val (key, value) = entry
                        val splitDate = key.split("-").map { it.toInt() }
                        HistoryCard(date = getDayKoreanWithoutYear(year, splitDate[0], splitDate[1]), list = value)
                    }
                }
            }
        }
    }
}

@Composable
private fun TypeCheckBoxGroup(
    modifier: Modifier,
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
                .background(if (isSelectedIncome) Purple else LightPurple)
                .padding(8.dp)
                .clickable {
                    onIncomeClick(!isSelectedIncome)
                },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PurpleCheckBox(checked = isSelectedIncome, onCheckedChange = onIncomeClick)
            Text(
                modifier = Modifier.padding(4.dp, 0.dp),
                text = "수입",
                textAlign = TextAlign.Center,
                color = White)
            Text(
                text = totalIncome.toString(), //TODO: 콤마 적용 필요
                textAlign = TextAlign.Center,
                color = White)
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .background(if (isSelectedExpenses) Purple else LightPurple)
                .padding(8.dp)
                .clickable {
                    onExpensesClick(!isSelectedExpenses)
                },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PurpleCheckBox(checked = isSelectedExpenses, onCheckedChange = onExpensesClick)
            Text(
                modifier = Modifier.padding(4.dp, 0.dp),
                text = "지출",
                textAlign = TextAlign.Center,
                color = White)
            Text(
                text = totalExpenses.toString(), //TODO: 콤마 적용 필요
                textAlign = TextAlign.Center,
                color = White)
        }
    }
}

enum class ScreenType {
    HISTORY, ADD_HISTORY, UPDATE_HISTORY
}