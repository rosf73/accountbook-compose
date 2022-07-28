package com.woowacamp.android_accountbook_15.ui.tabs.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
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
fun SettingScreen(
    viewModel: SettingViewModel
) {
    val (screenState, setScreenState) = remember { mutableStateOf(CardType.SETTING) }

    val state by viewModel.state.collectAsState()

    when (screenState) {
        CardType.SETTING -> SettingScreen(state) { type ->
            setScreenState(type)
        }
        CardType.PAYMENT_METHOD -> AddScreen(
            title = "결제 수단 추가",
            onAddClick = { text, _ -> viewModel.insertPaymentMethod(text) },
            onBackClick = { setScreenState(CardType.SETTING) }
        )
        CardType.EXPENSES_CATEGORY -> AddScreen(
            title = "지출 카테고리 추가",
            onAddClick = { text, color -> color?.let { viewModel.insertExpensesCategory(text, color) } },
            onBackClick = { setScreenState(CardType.SETTING) }
        )
        CardType.INCOME_CATEGORY -> AddScreen(
            title = "수입 카테고리 추가",
            onAddClick = { text, color -> color?.let { viewModel.insertExpensesCategory(text, color) } },
            onBackClick = { setScreenState(CardType.SETTING) }
        )
    }
}

@Composable
private fun SettingScreen(
    state: SettingViewState,
    onAddClick: (CardType) -> Unit
) {
    Scaffold(
        topBar = { Header(title = "설정") }
    ) {
        LazyColumn {
            item {
                SettingCard(
                    "결제 수단",
                    onAddClick = { onAddClick(CardType.PAYMENT_METHOD) }
                ) {
                    PaymentMethodCard(state.paymentMethods)
                }
                SettingCard(
                    "지출 카테고리",
                    onAddClick = { onAddClick(CardType.EXPENSES_CATEGORY) }
                ) {
                    CategoryCard(state.expensesCategories)
                }
                SettingCard(
                    "수입 카테고리",
                    onAddClick = { onAddClick(CardType.INCOME_CATEGORY) }
                ) {
                    CategoryCard(state.incomeCategories)
                }
            }
        }
    }
}

@Composable
fun SettingCard(
    title: String,
    onAddClick: () -> Unit,
    card: @Composable () -> Unit
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
        card()
        BottomItem(title, onAddClick)
    }
    Divider(color = Purple, thickness = 1.dp)
}

@Composable
private fun PaymentMethodCard(
    paymentMethods: List<PaymentMethod>
) {
    Column {
        paymentMethods.forEach { item ->
            SettingItem(item.name)
        }
    }
}

@Composable
private fun CategoryCard(
    categories: List<Category>
) {
    Column {
        categories.forEach { item ->
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
private fun BottomItem(
    title: String,
    onAddClick: () -> Unit
) {
    Row(modifier = Modifier.padding(0.dp, 11.dp)) {
        Text(
            modifier = Modifier.weight(1f),
            text = "$title 추가하기",
            fontSize = 14.sp,
            fontWeight = FontWeight(700)
        )
        IconButton(
            modifier = Modifier.then(
                Modifier
                    .size(14.dp)
                    .align(CenterVertically)),
            onClick = onAddClick
        ) {
            Icon(
                painterResource(R.drawable.ic_plus),
                ""
            )
        }
    }
}

enum class CardType {
    SETTING, PAYMENT_METHOD, EXPENSES_CATEGORY, INCOME_CATEGORY
}