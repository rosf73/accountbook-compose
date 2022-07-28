package com.woowacamp.android_accountbook_15.ui.tabs.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.woowacamp.android_accountbook_15.ui.components.Header
import com.woowacamp.android_accountbook_15.ui.theme.Purple

@Composable
fun SettingView(

) {
    LazyColumn(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
    ) {
        item {
            Header(
                title = "설정"
            )
            SettingCard("결제 수단")
            SettingCard("지출 카테고리")
            SettingCard("수입 카테고리")
        }
    }
}

@Composable
fun SettingCard(title: String) {
    Column {
        Text(text = title)
    }
}

@Preview
@Composable
fun SettingPreview() {
    SettingView()
}