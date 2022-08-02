package com.woowacamp.android_accountbook_15.ui.tabs.history

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.woowacamp.android_accountbook_15.data.model.History
import com.woowacamp.android_accountbook_15.ui.components.CategoryView
import com.woowacamp.android_accountbook_15.ui.components.CheckableItem
import com.woowacamp.android_accountbook_15.ui.theme.Blue
import com.woowacamp.android_accountbook_15.ui.theme.LightPurple
import com.woowacamp.android_accountbook_15.ui.theme.Purple04
import com.woowacamp.android_accountbook_15.ui.theme.Red
import com.woowacamp.android_accountbook_15.utils.toMoneyString

@Composable
fun HistoryCard(
    modifier: Modifier = Modifier,
    isSelectedIncome: Boolean,
    isSelectedExpenses: Boolean,
    date: String,
    list: List<History>,
    selectMode: Boolean,
    onSelect: (Boolean) -> Unit,
    onUpdateClick: (History) -> Unit,
) {
    Column(
        modifier = modifier.padding(16.dp, 8.dp)
    ) {
        Row(verticalAlignment = Alignment.Bottom) {
            Text(modifier = Modifier.weight(1f), text = date, color = LightPurple)
            Text(text = "수입  ${list.sumOf { if (it.type == 1) it.amount else 0}.toMoneyString()}" +
                    "  지출  ${list.sumOf { if (it.type == 0) it.amount else 0 }.toMoneyString()}",
                fontSize = 10.sp, color = LightPurple)
        }
        Spacer(modifier = Modifier.size(8.dp))

        Column {
            list.forEach { history ->
                if ((isSelectedIncome && history.type == 1) || (isSelectedExpenses && history.type == 0)) {
                    Divider(
                        color = Purple04,
                        thickness = 1.dp,
                        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 8.dp)
                    )
                    CheckableItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 8.dp),
                        isSelectMode = selectMode,
                        onPress = { /*TODO*/ },
                        onLongPress = { onSelect(true) },
                        onUpdateClick = { onUpdateClick(history) }) {
                        HistoryItem(history)
                    }
                }
            }
        }
    }
    Divider(color = LightPurple, thickness = 1.dp)
    Spacer(modifier = Modifier.size(16.dp))
}

@Composable
private fun HistoryItem(
    history: History
) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            history.category?.let {
                CategoryView(color = it.color, name = it.name)
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(text = history.payment?.name ?: "", fontSize = 10.sp)
        }

        Spacer(modifier = Modifier.size(8.dp))

        Row {
            Text(text = history.content ?: "", fontSize = 14.sp, fontWeight = FontWeight(700))
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "${history.amount.toMoneyString()}원", fontSize = 14.sp, color = if (history.type == 0) Red else Blue)
        }
    }
}