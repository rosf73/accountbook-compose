package com.woowacamp.android_accountbook_15.ui.tabs.graph

import androidx.activity.compose.BackHandler
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.painterResource
import com.woowacamp.android_accountbook_15.R
import com.woowacamp.android_accountbook_15.data.model.History
import com.woowacamp.android_accountbook_15.ui.components.Header

@Composable
fun DetailScreen(
    histories: List<History>,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            Header(
                title = "상세 내역 보기",
                leftIcon = painterResource(R.drawable.ic_back),
                onLeftClick = onBackClick
            )
        }
    ) {
        BackHandler {
            onBackClick()
        }

        Text(text = histories.toString())
    }
}