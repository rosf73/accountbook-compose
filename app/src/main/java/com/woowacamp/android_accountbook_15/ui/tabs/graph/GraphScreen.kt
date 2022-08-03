package com.woowacamp.android_accountbook_15.ui.tabs.graph

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.woowacamp.android_accountbook_15.R
import com.woowacamp.android_accountbook_15.ui.components.DatePicker
import com.woowacamp.android_accountbook_15.ui.components.Header
import com.woowacamp.android_accountbook_15.ui.tabs.history.HistoryViewModel
import com.woowacamp.android_accountbook_15.ui.theme.LightPurple
import com.woowacamp.android_accountbook_15.ui.theme.Purple
import com.woowacamp.android_accountbook_15.ui.theme.Red
import com.woowacamp.android_accountbook_15.ui.theme.Yellow
import com.woowacamp.android_accountbook_15.utils.getMonthAndYearKorean
import com.woowacamp.android_accountbook_15.utils.toMoneyString

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

        Column {
            val historiesEachCategory = viewModel.getExpensesEachCategory()
            Box(modifier = Modifier.padding(24.dp)) {
                AnimatedGraphCard(
                    historiesEachCategory.values.toList(),
                    historiesEachCategory.keys.map { Color(it.color) }.toList(),
                    Modifier
                        .height(300.dp)
                        .align(Alignment.Center)
                        .fillMaxWidth()
                )
                Column(modifier = Modifier.align(Alignment.Center)) {
                    Text(
                        text = "어디에 가장 많이 썼을까요?",
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        fontWeight = FontWeight(700)
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(
                        text = historiesEachCategory.values.sum().toInt().toMoneyString()+" 원 지출",
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = Red,
                        fontSize = 24.sp
                    )
                }
            }

            ExpensesCard(
                modifier = Modifier.padding(16.dp),
                expenses = historiesEachCategory)
            Divider(color = Purple, thickness = 1.dp)
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