package com.woowacamp.android_accountbook_15.ui.tabs.history

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.woowacamp.android_accountbook_15.R
import com.woowacamp.android_accountbook_15.ui.components.Header
import com.woowacamp.android_accountbook_15.utils.getTodayMonthAndYear

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel
) {
    Scaffold(
        topBar = {
            Header(
                title = getTodayMonthAndYear(),
                leftIcon = painterResource(R.drawable.ic_left),
                rightIcon = painterResource(R.drawable.ic_right)
            )
        }
    ) {

    }
}