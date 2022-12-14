package com.woowacamp.android_accountbook_15.ui.tabs.setting

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.woowacamp.android_accountbook_15.ui.components.Header
import com.woowacamp.android_accountbook_15.ui.theme.*

@Composable
fun SettingScreen(
    viewModel: SettingViewModel = hiltViewModel(),
    backToMain: () -> Unit
) {
    val (screenState, setScreenState) = remember { mutableStateOf(ScreenType.SETTING) }

    when (screenState) {
        ScreenType.SETTING -> SettingScreen(
            viewModel,
            onScreenChange = setScreenState,
            backToMain = backToMain)
        ScreenType.ADD_PAYMENT -> EditScreen(
            title = "결제 수단 추가",
            onAddClick = { text, _ -> viewModel.insertPaymentMethod(text) },
            onBackClick = { setScreenState(ScreenType.SETTING) })
        ScreenType.UPDATE_PAYMENT -> EditScreen(
            title = "결제 수단 수정",
            writtenName = viewModel.payment.collectAsState().value?.name,
            onAddClick = { text, _ -> viewModel.updatePaymentMethod(text) }, // viewModel의 paymentId 변경 필요
            onBackClick = { setScreenState(ScreenType.SETTING) })
        ScreenType.ADD_EXPENSES -> EditScreen(
            title = "지출 카테고리 추가",
            colors = expensesColors,
            onAddClick = { text, color -> viewModel.insertExpensesCategory(text, color!!) },
            onBackClick = { setScreenState(ScreenType.SETTING) })
        ScreenType.UPDATE_EXPENSES -> EditScreen(
            title = "지출 카테고리 수정",
            writtenName = viewModel.category.collectAsState().value?.name,
            selectedColor = viewModel.category.collectAsState().value?.color,
            colors = expensesColors,
            onAddClick = { text, color -> viewModel.updateCategory(text, color!!) },
            onBackClick = { setScreenState(ScreenType.SETTING) })
        ScreenType.ADD_INCOME -> EditScreen(
            title = "수입 카테고리 추가",
            colors = incomeColors,
            onAddClick = { text, color -> viewModel.insertIncomeCategory(text, color!!) },
            onBackClick = { setScreenState(ScreenType.SETTING) })
        ScreenType.UPDATE_INCOME -> EditScreen(
            title = "수입 카테고리 수정",
            writtenName = viewModel.category.collectAsState().value?.name,
            selectedColor = viewModel.category.collectAsState().value?.color,
            colors = incomeColors,
            onAddClick = { text, color -> viewModel.updateCategory(text, color!!) },
            onBackClick = { setScreenState(ScreenType.SETTING) })
    }
}

@Composable
private fun SettingScreen(
    viewModel: SettingViewModel,
    onScreenChange: (ScreenType) -> Unit,
    backToMain: () -> Unit
) {
    val paymentMethods = viewModel.paymentMethods
    val expensesCategories = viewModel.expensesCategories
    val incomeCategories = viewModel.incomeCategories

    Scaffold(
        topBar = { Header(title = "설정") }
    ) {
        BackHandler {
            backToMain()
        }

        LazyColumn {
            item {
                SettingCard(
                    "결제 수단",
                    onAddClick = { onScreenChange(ScreenType.ADD_PAYMENT) }
                ) {
                    PaymentMethodCard(
                        paymentMethods,
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
                        expensesCategories,
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
                        incomeCategories,
                        onScreenChange = { category ->
                            viewModel.category.value = category
                            onScreenChange(ScreenType.UPDATE_INCOME)
                        })
                }
            }
        }
    }
}

enum class ScreenType {
    SETTING, ADD_PAYMENT, UPDATE_PAYMENT, ADD_EXPENSES, UPDATE_EXPENSES, ADD_INCOME, UPDATE_INCOME
}