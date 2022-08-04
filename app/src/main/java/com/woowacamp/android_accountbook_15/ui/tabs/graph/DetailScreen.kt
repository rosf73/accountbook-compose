package com.woowacamp.android_accountbook_15.ui.tabs.graph

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.woowacamp.android_accountbook_15.R
import com.woowacamp.android_accountbook_15.data.model.History
import com.woowacamp.android_accountbook_15.ui.components.Header
import com.woowacamp.android_accountbook_15.ui.components.HistoryCard
import com.woowacamp.android_accountbook_15.ui.theme.White
import com.woowacamp.android_accountbook_15.utils.getDayKoreanWithoutYear

@Composable
fun DetailScreen(
    year: Int,
    amounts: List<Pair<Int, Long>>,
    histories: Map<String, List<History>>,
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

        Column() {
            ChartCard(amounts = amounts, modifier = Modifier.background(White).fillMaxWidth())

            LazyColumn {
                item {
                    histories.forEach { (key, value) ->
                        val splitDate = key.split("-").map { it.toInt() }
                        HistoryCard(
                            date = getDayKoreanWithoutYear(year, splitDate[0], splitDate[1]),
                            list = value)
                    }
                }
            }
        }
    }
}