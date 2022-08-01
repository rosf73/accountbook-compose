package com.woowacamp.android_accountbook_15.ui.tabs.history

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.woowacamp.android_accountbook_15.R
import com.woowacamp.android_accountbook_15.data.model.Category
import com.woowacamp.android_accountbook_15.data.model.History
import com.woowacamp.android_accountbook_15.data.model.PaymentMethod
import com.woowacamp.android_accountbook_15.ui.components.Header
import com.woowacamp.android_accountbook_15.ui.components.InputItem
import com.woowacamp.android_accountbook_15.ui.components.SpinnerItem
import com.woowacamp.android_accountbook_15.ui.theme.LightPurple
import com.woowacamp.android_accountbook_15.ui.theme.Purple
import com.woowacamp.android_accountbook_15.ui.theme.White
import com.woowacamp.android_accountbook_15.ui.theme.Yellow

@Composable
fun EditScreen(
    isCheckedIncome: Boolean,
    history: History? = null,
    onAddClick: (History) -> Unit,
    onBackClick: () -> Unit
) {
    val (isSelectedIncome, setIsSelectedIncome) = remember { mutableStateOf(isCheckedIncome) }

    val (date, setDate) = remember { mutableStateOf(history?.date ?: "") }
    val (amount, setAmount) = remember { mutableStateOf(history?.amount?.toString() ?: "") }
    val (paymentMethod, setPaymentMethod) = remember { mutableStateOf(history?.payment?.toString() ?: "") }
    val (category, setCategory) = remember { mutableStateOf(history?.category?.toString() ?: "") }
    val (content, setContent) = remember { mutableStateOf(history?.content ?: "") }

    val (isPaymentOpened, setPaymentOpened) = remember { mutableStateOf(false) }
    val (isCategoryOpened, setCategoryOpened) = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Header(
                title = "내역 등록",
                leftIcon = painterResource(R.drawable.ic_back),
                leftCallback = onBackClick
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
            }

            EditScreen(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                isSelectedExpenses = !isSelectedIncome,
                date, amount, paymentMethod, category, content,
                setDate, setAmount, setPaymentMethod, setCategory, setContent,
                isPaymentOpened, isCategoryOpened, setPaymentOpened, setCategoryOpened
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
                            date = date,
                            amount = amount.toInt(),
                            payment = PaymentMethod(-1, paymentMethod),
                            category = Category(-1, isIncome, category, 0x0),
                            content = content
                        )
                    )
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Yellow),
                contentPadding = PaddingValues(16.dp),
                enabled = date.isNotBlank() && amount.isNotBlank() && (isSelectedIncome || paymentMethod.isNotBlank())
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
        modifier = modifier.padding(0.dp, 0.dp, 0.dp, 16.dp).clip(RoundedCornerShape(10.dp))
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
            color = White)
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
            color = White)
    }
}

@Composable
private fun EditScreen(
    modifier: Modifier,
    isSelectedExpenses: Boolean,
    date: String,
    amount: String,
    paymentMethod: String,
    category: String,
    content: String,
    onDateChanged: (String) -> Unit,
    onAmountChanged: (String) -> Unit,
    onPaymentChanged: (String) -> Unit,
    onCategoryChanged: (String) -> Unit,
    onContentChanged: (String) -> Unit,
    isPaymentOpened: Boolean,
    isCategoryOpened: Boolean,
    setPaymentOpened: (Boolean) -> Unit,
    setCategoryOpened: (Boolean) -> Unit
) {
    Box(modifier = modifier) {
        LazyColumn(
            modifier = modifier.align(Alignment.TopCenter)
        ) {
            item {
                InputItem(
                    label = "일자",
                    value = date,
                    onTextChanged = onDateChanged)
                InputItem(
                    label = "금액",
                    value = amount,
                    onTextChanged = onAmountChanged,
                    numeric = true)
                if (isSelectedExpenses)
                    SpinnerItem(
                        label = "결제 수단",
                        value = paymentMethod,
                        list = listOf("카드1", "카드2"),
                        requestToOpen = isPaymentOpened,
                        onOpen = setPaymentOpened,
                        onTextChanged = onPaymentChanged)
                SpinnerItem(
                    label = "분류",
                    value = category,
                    list = listOf("지출일까", "수입일까"),
                    requestToOpen = isCategoryOpened,
                    onOpen = setCategoryOpened,
                    onTextChanged = onCategoryChanged)
                InputItem(
                    label = "내용",
                    value = content,
                    onTextChanged = onContentChanged)
            }
        }
    }
}