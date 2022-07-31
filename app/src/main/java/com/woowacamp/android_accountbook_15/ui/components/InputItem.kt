package com.woowacamp.android_accountbook_15.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.woowacamp.android_accountbook_15.ui.theme.LightPurple
import com.woowacamp.android_accountbook_15.ui.theme.Purple04

@Composable
fun InputItem(
    modifier: Modifier,
    title: String,
    value: String,
    onTextChanged: (String) -> Unit
) {
    Row(modifier = modifier) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .width(76.dp),
            text = title)
        TextField(
            modifier = Modifier.weight(1f),
            value = value,
            onValueChange = onTextChanged,
            placeholder = {
                Text(text = "입력하세요", color = LightPurple)
            })
    }
    Divider(color = Purple04, thickness = 1.dp)
}