package com.woowacamp.android_accountbook_15.ui.tabs.history

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.woowacamp.android_accountbook_15.R
import com.woowacamp.android_accountbook_15.data.model.Category
import com.woowacamp.android_accountbook_15.data.model.History
import com.woowacamp.android_accountbook_15.data.model.PaymentMethod
import com.woowacamp.android_accountbook_15.ui.components.*
import com.woowacamp.android_accountbook_15.ui.tabs.setting.SettingViewModel
import com.woowacamp.android_accountbook_15.ui.theme.*
import com.woowacamp.android_accountbook_15.utils.*

@Composable
fun EditScreen(
    title: String,
    viewModel: SettingViewModel,
    isCheckedIncome: Boolean,
    history: History? = null,
    onAddClick: (History) -> Unit,
    onBackClick: () -> Unit
) {
    val (isSelectedIncome, setIsSelectedIncome) = remember { mutableStateOf(
        if (history != null)
            history.type == 1
        else
            isCheckedIncome
    ) }

    val (date, setDate) = remember { mutableStateOf(if (history != null) changeHyphenToKorean(history.date) else getTodayKorean()) }
    val (amount, setAmount) = remember { mutableStateOf(TextFieldValue(history?.amount?.toString() ?: "")) }
    val (paymentMethod, setPaymentMethod) = remember { mutableStateOf(history?.payment?.name ?: "") }
    val (category, setCategory) = remember { mutableStateOf(history?.category?.name ?: "") }
    val (paymentId, setPaymentId) = remember { mutableStateOf(history?.payment?.id ?: -1L) }
    val (categoryId, setCategoryId) = remember { mutableStateOf(history?.category?.id ?: -1L) }
    val (content, setContent) = remember { mutableStateOf(history?.content ?: "") }

    val (isDateOpened, setDateOpened) = remember { mutableStateOf(false) }
    val (isPaymentOpened, setPaymentOpened) = remember { mutableStateOf(false) }
    val (isCategoryOpened, setCategoryOpened) = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Header(
                title = title,
                leftIcon = painterResource(R.drawable.ic_back),
                onLeftClick = onBackClick
            )
        }
    ) {
        BackHandler {
            onBackClick()
        }

        Column(modifier = Modifier.padding(16.dp)) {
            TypeRadioGroup(
                modifier = Modifier.fillMaxWidth(),
                isSelectedIncome = isSelectedIncome
            ) { type ->
                setIsSelectedIncome(type)
                setCategory("")
                setCategoryId(-1)
            }

            EditScreen(
                viewModel,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                isSelectedExpenses = !isSelectedIncome,
                date, amount, paymentMethod, category, content,
                setDate, setAmount, setPaymentMethod, setCategory, setPaymentId, setCategoryId, setContent,
                isDateOpened, isPaymentOpened, isCategoryOpened,
                setDateOpened, setPaymentOpened, setCategoryOpened,
                onAddPayment = { viewModel.insertPaymentMethod(it) },
                onAddCategory = {
                    if (isSelectedIncome) viewModel.insertIncomeCategory(it, 0xFF9BD182)
                    else viewModel.insertExpensesCategory(it, 0xFF4A6CC3)
                }
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 20.dp),
                onClick = {
                    val isIncome = if (isSelectedIncome) 1 else 0
                    onAddClick(
                        History(
                            -1,
                            isIncome,
                            date = changeKoreanToHyphen(date),
                            amount = amount.text.toMoneyInt(),
                            payment = PaymentMethod(paymentId, paymentMethod),
                            category = Category(categoryId, isIncome, category, 0x0),
                            content = content
                        )
                    )
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Yellow, disabledBackgroundColor = Yellow05),
                contentPadding = PaddingValues(16.dp),
                enabled = date.isNotBlank() && amount.text.isNotBlank() && amount.text != "0" && (isSelectedIncome || paymentMethod.isNotBlank())
            ) {
                Text(text = "등록하기", color = White)
            }
        }
    }
}

@Composable
private fun TypeRadioGroup(
    modifier: Modifier,
    isSelectedIncome: Boolean,
    onClick: (Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .padding(0.dp, 0.dp, 0.dp, 16.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .background(if (isSelectedIncome) Purple else LightPurple)
                .padding(8.dp)
                .clickable {
                    onClick(true)
                },
            text = "수입",
            textAlign = TextAlign.Center,
            color = White,
            fontSize = 12.sp)
        Text(
            modifier = Modifier
                .weight(1f)
                .background(if (isSelectedIncome) LightPurple else Purple)
                .padding(8.dp)
                .clickable {
                    onClick(false)
                },
            text = "지출",
            textAlign = TextAlign.Center,
            color = White,
            fontSize = 12.sp)
    }
}

@Composable
private fun EditScreen(
    viewModel: SettingViewModel,
    modifier: Modifier,
    isSelectedExpenses: Boolean,
    date: String,
    amount: TextFieldValue,
    paymentMethod: String,
    category: String,
    content: String,
    onDateChanged: (String) -> Unit,
    onAmountChanged: (TextFieldValue) -> Unit,
    onPaymentChanged: (String) -> Unit,
    onCategoryChanged: (String) -> Unit,
    onPaymentIdChanged: (Long) -> Unit,
    onCategoryIdChanged: (Long) -> Unit,
    onContentChanged: (String) -> Unit,
    isDateOpened: Boolean,
    isPaymentOpened: Boolean,
    isCategoryOpened: Boolean,
    setDateOpened: (Boolean) -> Unit,
    setPaymentOpened: (Boolean) -> Unit,
    setCategoryOpened: (Boolean) -> Unit,
    onAddPayment: (String) -> Long?,
    onAddCategory: (String) -> Long?
) {
    val paymentMethods = viewModel.paymentMethods
    val expensesCategories = viewModel.expensesCategories
    val incomeCategories = viewModel.incomeCategories

    Box(modifier = modifier) {
        LazyColumn(
            modifier = modifier.align(Alignment.TopCenter)
        ) {
            item {
                DateSpinnerItem(
                    label = "일자",
                    value = date,
                    requestToOpen = isDateOpened,
                    onOpen = setDateOpened,
                    onTextChanged = onDateChanged)
                MoneyInputItem(
                    label = "금액",
                    value = amount,
                    onTextChanged = onAmountChanged)
                if (isSelectedExpenses)
                    SpinnerItem(
                        label = "결제 수단",
                        value = paymentMethod,
                        valueList = paymentMethods.map { it.id },
                        textList = paymentMethods.map { it.name },
                        requestToOpen = isPaymentOpened,
                        onOpen = setPaymentOpened,
                        onTextChanged = { value, text ->
                            onPaymentIdChanged(value)
                            onPaymentChanged(text)
                        },
                        onAddClick = onAddPayment)
                SpinnerItem(
                    label = "분류",
                    value = category,
                    valueList = if (isSelectedExpenses)
                        expensesCategories.map { it.id }
                    else incomeCategories.map { it.id },
                    textList = if (isSelectedExpenses)
                        expensesCategories.map { it.name }
                    else incomeCategories.map { it.name },
                    requestToOpen = isCategoryOpened,
                    onOpen = setCategoryOpened,
                    onTextChanged = { value, text ->
                        onCategoryIdChanged(value)
                        onCategoryChanged(text)
                    },
                    onAddClick = onAddCategory)
                InputItem(
                    label = "내용",
                    value = content,
                    onTextChanged = onContentChanged)
            }
        }
    }
}