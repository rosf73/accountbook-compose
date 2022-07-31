package com.woowacamp.android_accountbook_15.ui.tabs.setting

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.woowacamp.android_accountbook_15.R
import com.woowacamp.android_accountbook_15.data.model.Category
import com.woowacamp.android_accountbook_15.data.model.PaymentMethod
import com.woowacamp.android_accountbook_15.ui.components.Header
import com.woowacamp.android_accountbook_15.ui.theme.*

@Composable
fun SettingScreen(
    viewModel: SettingViewModel
) {
    val (screenState, setScreenState) = remember { mutableStateOf(ScreenType.SETTING) }

    when (screenState) {
        ScreenType.SETTING -> SettingScreen(viewModel) { type -> setScreenState(type) }
        ScreenType.ADD_PAYMENT -> AddScreen(
            title = "결제 수단 추가",
            onAddClick = { text, _ -> viewModel.insertPaymentMethod(text) },
            onBackClick = { setScreenState(ScreenType.SETTING) })
        ScreenType.UPDATE_PAYMENT -> AddScreen(
            title = "결제 수단 수정",
            writtenName = viewModel.payment.collectAsState().value?.name,
            onAddClick = { text, _ -> viewModel.updatePaymentMethod(text) }, // viewModel의 paymentId 변경 필요
            onBackClick = { setScreenState(ScreenType.SETTING) })
        ScreenType.ADD_EXPENSES -> AddScreen(
            title = "지출 카테고리 추가",
            colors = expensesColors,
            onAddClick = { text, color -> viewModel.insertExpensesCategory(text, color!!) },
            onBackClick = { setScreenState(ScreenType.SETTING) })
        ScreenType.UPDATE_EXPENSES -> AddScreen(
            title = "지출 카테고리 수정",
            writtenName = viewModel.category.collectAsState().value?.name,
            selectedColor = viewModel.category.collectAsState().value?.color,
            colors = expensesColors,
            onAddClick = { text, color -> viewModel.updateCategory(text, color!!) },
            onBackClick = { setScreenState(ScreenType.SETTING) })
        ScreenType.ADD_INCOME -> AddScreen(
            title = "수입 카테고리 추가",
            writtenName = viewModel.payment.collectAsState().value?.name,
            colors = incomeColors,
            onAddClick = { text, color -> viewModel.insertIncomeCategory(text, color!!) },
            onBackClick = { setScreenState(ScreenType.SETTING) })
        ScreenType.UPDATE_INCOME -> AddScreen(
            title = "지출 카테고리 수정",
            writtenName = viewModel.category.collectAsState().value?.name,
            selectedColor = viewModel.category.collectAsState().value?.color,
            colors = expensesColors,
            onAddClick = { text, color -> viewModel.updateCategory(text, color!!) },
            onBackClick = { setScreenState(ScreenType.SETTING) })
    }
}

@Composable
private fun SettingScreen(
    viewModel: SettingViewModel,
    onScreenChange: (ScreenType) -> Unit
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            Header(title = "설정")
        }
    ) {
        LazyColumn {
            item {
                SettingCard(
                    "결제 수단",
                    onAddClick = { onScreenChange(ScreenType.ADD_PAYMENT) }
                ) {
                    PaymentMethodCard(
                        state.paymentMethods,
                        onScreenChange = { payment ->
                            viewModel.payment.value = payment
                            onScreenChange(ScreenType.UPDATE_PAYMENT)
                        })
                }
                SettingCard(
                    "지출 카테고리",
                    onAddClick = { onScreenChange(ScreenType.ADD_EXPENSES) }
                ) {
                    CategoryCard(
                        state.expensesCategories,
                        onScreenChange = { category ->
                            viewModel.category.value = category
                            onScreenChange(ScreenType.UPDATE_EXPENSES)
                        })
                }
                SettingCard(
                    "수입 카테고리",
                    onAddClick = { onScreenChange(ScreenType.ADD_INCOME) }
                ) {
                    CategoryCard(
                        state.incomeCategories,
                        onScreenChange = { category ->
                            viewModel.category.value = category
                            onScreenChange(ScreenType.UPDATE_INCOME)
                        })
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
    paymentMethods: List<PaymentMethod>,
    onScreenChange: (PaymentMethod) -> Unit
) {
    Column {
        paymentMethods.forEach { item ->
            SettingItem(
                item.name,
                onClick = { onScreenChange(item) },
                onLongClick = {})
        }
    }
}

@Composable
private fun CategoryCard(
    categories: List<Category>,
    onScreenChange: (Category) -> Unit
) {
    Column {
        categories.forEach { item ->
            SettingItem(
                item.name,
                item.color,
                onClick = { onScreenChange(item) },
                onLongClick = {})
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SettingItem(
    name: String,
    color: Long? = null,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(0.dp, 11.dp)
            .fillMaxWidth()
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
    ) {
        Text(
            modifier = Modifier.align(CenterStart),
            text = name,
            fontSize = 14.sp,
            fontWeight = FontWeight(700)
        )
        color?.let {
            Text(modifier = Modifier
                .width(56.dp)
                .align(CenterEnd)
                .clip(RoundedCornerShape(14.dp))
                .background(Color(color))
                .padding(8.dp, 4.dp),
                text = name,
                color = White,
                fontSize = 10.sp,
                textAlign = TextAlign.Center)
        }
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

enum class ScreenType {
    SETTING, ADD_PAYMENT, UPDATE_PAYMENT, ADD_EXPENSES, UPDATE_EXPENSES, ADD_INCOME, UPDATE_INCOME
}