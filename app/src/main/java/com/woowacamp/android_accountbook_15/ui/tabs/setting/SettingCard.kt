package com.woowacamp.android_accountbook_15.ui.tabs.setting

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import com.woowacamp.android_accountbook_15.ui.theme.LightPurple
import com.woowacamp.android_accountbook_15.ui.theme.Purple
import com.woowacamp.android_accountbook_15.ui.theme.Purple04
import com.woowacamp.android_accountbook_15.ui.theme.White

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
fun PaymentMethodCard(
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
fun CategoryCard(
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
            modifier = Modifier.align(Alignment.CenterStart),
            text = name,
            fontSize = 14.sp,
            fontWeight = FontWeight(700)
        )
        color?.let {
            Text(modifier = Modifier
                .width(56.dp)
                .align(Alignment.CenterEnd)
                .clip(RoundedCornerShape(14.dp))
                .background(Color(color))
                .padding(0.dp, 4.dp),
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
                    .align(Alignment.CenterVertically)),
            onClick = onAddClick
        ) {
            Icon(
                painterResource(R.drawable.ic_plus),
                ""
            )
        }
    }
}