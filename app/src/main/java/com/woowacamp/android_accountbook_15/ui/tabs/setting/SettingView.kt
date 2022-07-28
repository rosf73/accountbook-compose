package com.woowacamp.android_accountbook_15.ui.tabs.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.woowacamp.android_accountbook_15.R
import com.woowacamp.android_accountbook_15.data.model.Category
import com.woowacamp.android_accountbook_15.data.model.PaymentMethod
import com.woowacamp.android_accountbook_15.ui.components.Header
import com.woowacamp.android_accountbook_15.ui.theme.LightPurple
import com.woowacamp.android_accountbook_15.ui.theme.Purple
import com.woowacamp.android_accountbook_15.ui.theme.Purple04

@Composable
fun SettingView(
    viewModel: SettingViewModel
) {
    val state by viewModel.state.collectAsState()

    LazyColumn {
        item {
            Header(title = "설정")
            SettingCard("결제 수단", CardType.PAYMENT_METHOD, state)
            Divider(color = Purple, thickness = 1.dp)
            SettingCard("지출 카테고리", CardType.EXPENSES_CATEGORY, state)
            Divider(color = Purple, thickness = 1.dp)
            SettingCard("수입 카테고리", CardType.INCOME_CATEGORY, state)
        }
    }
}

@Composable
fun SettingCard(
    title: String,
    type: CardType,
    state: SettingViewState
) {
    Column(
        modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp)
    ) {
        Text(
            modifier = Modifier.padding(0.dp, 8.dp),
            text = title,
            fontSize = 16.sp,
            color = LightPurple
        )
        Divider(color = Purple04, thickness = 1.dp)
        when (type) {
            CardType.PAYMENT_METHOD -> PaymentMethodCard(state.paymentMethods)
            CardType.EXPENSES_CATEGORY -> CategoryCard(state.expensesCategories)
            CardType.INCOME_CATEGORY -> CategoryCard(state.incomeCategories)
        }
        BottomItem(title)
    }
}

@Composable
private fun PaymentMethodCard(
    list: List<PaymentMethod>
) {
    Column {
        list.forEach { item ->
            SettingItem(item.name)
        }
    }
}

@Composable
private fun CategoryCard(
    list: List<Category>
) {
    Column {
        list.forEach { item ->
            SettingItem(item.name, item.color)
        }
    }
}

@Composable
private fun SettingItem(
    name: String,
    color: String? = null
) {
    Row(modifier = Modifier.padding(0.dp, 11.dp)) {
       Text(
           text = name,
           fontSize = 14.sp,
           fontWeight = FontWeight(700)
       )
       color?.let { Text(text = name) }
    }
    Divider(color = Purple04, thickness = 1.dp)
}

@Composable
private fun BottomItem(title: String) {
    Row(modifier = Modifier.padding(0.dp, 11.dp)) {
        Text(
            modifier = Modifier.weight(1f),
            text = "$title 추가하기",
            fontSize = 14.sp,
            fontWeight = FontWeight(700)
        )
        IconButton(
            modifier = Modifier.then(Modifier.size(14.dp)),
            onClick = { /*TODO*/ }
        ) {
            Icon(
                painterResource(R.drawable.ic_plus),
                ""
            )
        }
    }
}

enum class CardType {
    PAYMENT_METHOD, EXPENSES_CATEGORY, INCOME_CATEGORY
}