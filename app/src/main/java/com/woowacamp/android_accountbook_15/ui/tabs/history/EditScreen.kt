package com.woowacamp.android_accountbook_15.ui.tabs.history

import androidx.activity.compose.BackHandler
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.woowacamp.android_accountbook_15.R
import com.woowacamp.android_accountbook_15.data.model.History
import com.woowacamp.android_accountbook_15.ui.components.Header

@Composable
fun EditScreen(
    history: History? = null,
    onAddClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            Header(
                title = "내역 등록",
                leftIcon = painterResource(R.drawable.ic_back),
                leftCallback = onBackClick
            )
        }
    ) {
        BackHandler {
            onBackClick()
        }
    }
}