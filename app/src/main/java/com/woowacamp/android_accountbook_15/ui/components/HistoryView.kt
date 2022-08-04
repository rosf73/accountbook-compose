package com.woowacamp.android_accountbook_15.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.woowacamp.android_accountbook_15.data.model.History
import com.woowacamp.android_accountbook_15.ui.theme.*
import com.woowacamp.android_accountbook_15.utils.toMoneyString


@Composable
fun HistoryCard(
    modifier: Modifier = Modifier,
    isSelectedIncome: Boolean,
    isSelectedExpenses: Boolean,
    date: String,
    list: List<History>,
    selectMode: Boolean,
    onPress: (Long) -> Unit,
    onLongPress: (Long) -> Unit,
    onUpdateClick: (History) -> Unit,
) {
    Column {
        Row(
            modifier = modifier.padding(16.dp, 8.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(modifier = Modifier.weight(1f), text = date, color = LightPurple)
            Text(text = "수입  ${list.sumOf { if (it.type == 1) it.amount else 0}.toMoneyString()}" +
                    "  지출  ${(list.sumOf { if (it.type == 0) it.amount else 0 }*-1).toMoneyString()}",
                fontSize = 10.sp, color = LightPurple
            )
        }

        Column {
            list.forEach { history ->
                if ((isSelectedIncome && history.type == 1) || (isSelectedExpenses && history.type == 0)) {
                    Divider(color = Purple04, thickness = 1.dp, modifier = Modifier.padding(16.dp, 0.dp))
                    CheckableItem(
                        modifier = Modifier
                            .fillMaxWidth(),
                        isSelectMode = selectMode,
                        onPress = { onPress(history.id) },
                        onLongPress = { onLongPress(history.id) },
                        onUpdateClick = { onUpdateClick(history) }
                    ) {
                        HistoryItem(history = history)
                    }
                }
            }
        }
    }
    Divider(color = LightPurple, thickness = 1.dp)
    Spacer(modifier = Modifier.size(16.dp))
}

@Composable
fun HistoryCard(
    modifier: Modifier = Modifier,
    date: String,
    list: List<History>
) {
    Column {
        Row(
            modifier = modifier.padding(16.dp, 8.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(modifier = Modifier.weight(1f), text = date, color = LightPurple)
            Text(text = "지출  ${(list.sumOf { if (it.type == 0) it.amount else 0 }*-1).toMoneyString()}",
                fontSize = 10.sp, color = LightPurple)
        }

        Column {
            list.forEach { history ->
                Divider(color = Purple04, thickness = 1.dp, modifier = Modifier.padding(16.dp, 0.dp))
                HistoryItem(history = history)
            }
        }
    }
    Divider(color = LightPurple, thickness = 1.dp)
    Spacer(modifier = Modifier.size(16.dp))
}

@Composable
private fun HistoryItem(
    modifier: Modifier = Modifier,
    history: History
) {
    Column(modifier = modifier
        .padding(16.dp, 8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            CategoryView(color = history.category.color, name = history.category.name)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = history.payment?.name ?: "", fontSize = 10.sp)
        }

        Spacer(modifier = Modifier.size(8.dp))

        Row {
            val amount = if (history.type == 0) "-"+history.amount.toMoneyString() else history.amount.toMoneyString()
            Text(text = history.content ?: "", fontSize = 14.sp, fontWeight = FontWeight(700))
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "${amount}원", fontSize = 14.sp, color = if (history.type == 0) Red else Blue)
        }
    }
}