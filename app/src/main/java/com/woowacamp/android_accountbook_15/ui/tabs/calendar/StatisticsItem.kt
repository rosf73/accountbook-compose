package com.woowacamp.android_accountbook_15.ui.tabs.calendar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.woowacamp.android_accountbook_15.utils.toMoneyString

@Composable
fun StatisticsItem(
    label: String,
    amount: Int,
    amountColor: Color
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp, 8.dp)
    ) {
        Text(modifier = Modifier.weight(1f), text = label)
        Text(text = amount.toMoneyString(), color = amountColor)
    }
}