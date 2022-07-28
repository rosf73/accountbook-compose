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
import androidx.compose.ui.unit.sp
import com.woowacamp.android_accountbook_15.R
import com.woowacamp.android_accountbook_15.ui.components.Header
import com.woowacamp.android_accountbook_15.ui.components.Palette
import com.woowacamp.android_accountbook_15.ui.theme.LightPurple
import com.woowacamp.android_accountbook_15.ui.theme.Purple04
import com.woowacamp.android_accountbook_15.ui.theme.White
import com.woowacamp.android_accountbook_15.ui.theme.Yellow

@Composable
fun AddScreen(
    title: String,
    colors: List<Long>? = null,
    onAddClick: (String, Long?) -> Unit,
    onBackClick: () -> Unit
) {
    val (name, setName) = remember { mutableStateOf("") }
    val (color, setColor) = remember { mutableStateOf(0xFFFFFFFF) }

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
            colors = colors,
            text = name,
            onColorSelect = { color -> setColor(color) },
            onTextChanged = { text -> setName(text) },
            onAddClick = {
                onAddClick(name, color)
                onBackClick()
            }
        )
    }
}

@Composable
private fun AddScreen(
    modifier: Modifier,
    colors: List<Long>?,
    text: String,
    onColorSelect: (Long) -> Unit,
    onTextChanged: (String) -> Unit,
    onAddClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
    ) {
        Column(
            modifier = modifier
            .align(Alignment.TopCenter)
            .padding(16.dp)
        ) {
            Row(
                modifier = modifier.padding(4.dp)
            ) {
                Text(
                    modifier = Modifier.align(CenterVertically),
                    text = "이름")
                TextField(
                    modifier = Modifier.weight(1f),
                    value = text,
                    onValueChange = onTextChanged,
                    placeholder = {
                        Text(text = "입력하세요", color = LightPurple)
                    })
            }
            Divider(color = Purple04, thickness = 1.dp)

            colors?.let {
                Text(
                    modifier = modifier.padding(0.dp, 16.dp),
                    text = "색상",
                    fontSize = 16.sp,
                    color = LightPurple
                )
                Divider(color = Purple04, thickness = 1.dp)
                Palette(
                    modifier = modifier.padding(16.dp),
                    colors = colors,
                    onColorSelect = onColorSelect)
            }
        }

        Button(
            modifier = modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp),
            onClick = onAddClick,
            colors = ButtonDefaults.buttonColors(backgroundColor = Yellow),
            contentPadding = PaddingValues(16.dp)
        ) {
            Text(text = "등록하기", color = White)
        }
    }
}