package com.woowacamp.android_accountbook_15.ui.tabs.setting

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.woowacamp.android_accountbook_15.R
import com.woowacamp.android_accountbook_15.ui.components.Header
import com.woowacamp.android_accountbook_15.ui.theme.LightPurple
import com.woowacamp.android_accountbook_15.ui.theme.Purple04
import com.woowacamp.android_accountbook_15.ui.theme.White
import com.woowacamp.android_accountbook_15.ui.theme.Yellow

@Composable
fun AddScreen(
    title: String,
    colors: List<String>? = null,
    onAddClick: (String, String?) -> Unit,
    onBackClick: () -> Unit
) {
    val (name, setName) = remember { mutableStateOf("") }
    val (color, setColor) = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            Header(
                title = title,
                leftIcon = painterResource(R.drawable.ic_back),
                leftCallback = onBackClick
            )
        }
    ) {
        BackHandler {
            onBackClick()
        }

        AddScreen(
            modifier = Modifier.fillMaxWidth(),
            onTextChanged = { text -> setName(text) },
            onAddClick = { onAddClick(name, color) }
        )
    }
}

@Composable
private fun AddScreen(
    modifier: Modifier,
    onTextChanged: (String) -> Unit,
    onAddClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .padding(20.dp)
    ) {
        Column(
            modifier = modifier
            .align(Alignment.TopCenter)
        ) {
            Row(modifier = modifier
                .padding(0.dp, 8.dp)
            ) {
                Text(
                    modifier = Modifier.align(CenterVertically),
                    text = "이름")
                TextField(
                    modifier = Modifier.weight(1f),
                    value = "",
                    onValueChange = onTextChanged,
                    placeholder = {
                        Text(text = "입력하세요", color = LightPurple)
                    })
            }
            Divider(color = Purple04, thickness = 1.dp)
        }

        Button(
            modifier = modifier
                .align(Alignment.BottomCenter),
            onClick = onAddClick,
            colors = ButtonDefaults.buttonColors(backgroundColor = Yellow),
            contentPadding = PaddingValues(16.dp)
        ) {
            Text(text = "등록하기", color = White)
        }
    }
}