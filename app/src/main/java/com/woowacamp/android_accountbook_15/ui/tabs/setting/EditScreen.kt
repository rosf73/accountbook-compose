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
import com.woowacamp.android_accountbook_15.ui.components.InputItem
import com.woowacamp.android_accountbook_15.ui.components.Palette
import com.woowacamp.android_accountbook_15.ui.theme.LightPurple
import com.woowacamp.android_accountbook_15.ui.theme.Purple04
import com.woowacamp.android_accountbook_15.ui.theme.White
import com.woowacamp.android_accountbook_15.ui.theme.Yellow

@Composable
fun EditScreen(
    title: String,
    writtenName: String? = null,
    selectedColor: Long? = null,
    colors: List<Long>? = null,
    onAddClick: (String, Long?) -> Unit,
    onBackClick: () -> Unit
) {
    val (name, setName) = remember { mutableStateOf(writtenName ?: "") }
    val (color, setColor) = remember { mutableStateOf(selectedColor) }

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

        EditScreen(
            modifier = Modifier.fillMaxWidth(),
            text = name,
            selectedColor = color,
            colors = colors,
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
private fun EditScreen(
    modifier: Modifier,
    text: String,
    selectedColor: Long? = null,
    colors: List<Long>?,
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
            InputItem(
                label = "이름",
                value = text,
                onTextChanged = onTextChanged)

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
                    initColor = selectedColor,
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
            contentPadding = PaddingValues(16.dp),
            enabled = text.isNotBlank()
        ) {
            Text(text = "등록하기", color = White)
        }
    }
}