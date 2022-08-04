package com.woowacamp.android_accountbook_15.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.woowacamp.android_accountbook_15.R
import com.woowacamp.android_accountbook_15.data.model.History
import com.woowacamp.android_accountbook_15.ui.theme.*
import com.woowacamp.android_accountbook_15.utils.getDayKorean
import com.woowacamp.android_accountbook_15.utils.toMoneyInt
import com.woowacamp.android_accountbook_15.utils.toMoneyString


@Composable
fun DateSpinnerItem(
    label: String,
    value: String,
    requestToOpen: Boolean = false,
    onOpen: (Boolean) -> Unit,
    onTextChanged: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 8.dp)
            .clickable {
                onOpen(true)
            }
    ) {
        Text(
            modifier = Modifier
                .align(CenterVertically)
                .width(76.dp),
            text = label,
            fontSize = 14.sp)
        Text(
            modifier = Modifier
                .align(CenterVertically)
                .weight(1f),
            text = value,
            color = Purple,
            fontSize = 14.sp)
    }
    Divider(color = Purple04, thickness = 1.dp, modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 8.dp))

    if (requestToOpen) {
        val splitDate = value.split(". ")
        DatePicker(
            initYear = splitDate[0].toInt(),
            initMonth = splitDate[1].toInt(),
            initDate = splitDate[2].split(" ")[0].toInt(),
            onOpen = onOpen,
            onDateChanged = { y, m, d -> onTextChanged(getDayKorean(y, m, d)) })
    }
}

@Composable
fun InputItem(
    label: String,
    value: String,
    onTextChanged: (String) -> Unit
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp, 8.dp)) {
        Text(
            modifier = Modifier
                .align(CenterVertically)
                .width(76.dp),
            text = label,
            fontSize = 14.sp)
        BasicTextField(
            modifier = Modifier.weight(1f),
            value = value,
            onValueChange = { onTextChanged(it) },
            singleLine = true,
            textStyle = TextStyle(color = Purple),
            decorationBox = { innerTextField ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    if (value.isEmpty()) {
                        Text(
                            text = "입력하세요",
                            color = LightPurple,
                            fontSize = 14.sp)
                    }
                }
                innerTextField()
            })
    }
    Divider(color = Purple04, thickness = 1.dp, modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 8.dp))
}

@Composable
fun MoneyInputItem(
    label: String,
    value: TextFieldValue,
    onTextChanged: (TextFieldValue) -> Unit
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp, 8.dp)) {
        Text(
            modifier = Modifier
                .align(CenterVertically)
                .width(76.dp),
            text = label,
            fontSize = 14.sp)
        BasicTextField(
            modifier = Modifier.weight(1f),
            value = value,
            onValueChange = {
                if (it.text.isEmpty())
                    onTextChanged(TextFieldValue("0", selection = TextRange(1)))
                else if (it.text.length < 12) {
                    val moneyString = it.text.toMoneyInt().toMoneyString()
                    onTextChanged(TextFieldValue(moneyString, selection = TextRange(moneyString.length)))
                }
            },
            singleLine = true,
            textStyle = TextStyle(color = Purple),
            decorationBox = { innerTextField ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    if (value.text.isEmpty()) {
                        Text(
                            text = "입력하세요",
                            color = LightPurple,
                            fontSize = 14.sp)
                    }
                }
                innerTextField()
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
    }
    Divider(color = Purple04, thickness = 1.dp, modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 8.dp))
}

@Composable
fun SpinnerItem(
    label: String,
    value: String,
    valueList: List<Long>,
    textList: List<String>,
    requestToOpen: Boolean = false,
    onOpen: (Boolean) -> Unit,
    onTextChanged: (Long, String) -> Unit,
    onAddClick: (String) -> Long?
) {
    var addText by remember { mutableStateOf("") }

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp, 8.dp)) {
        Text(
            modifier = Modifier
                .align(CenterVertically)
                .width(76.dp),
            text = label,
            fontSize = 14.sp)
        Text(
            modifier = Modifier
                .align(CenterVertically)
                .weight(1f),
            text = if (value == "") "선택하세요" else value,
            color = if (value == "") LightPurple else Purple,
            fontSize = 14.sp)
        Box(modifier = Modifier.align(CenterVertically)) {
            Icon(
                modifier = Modifier
                    .clickable {
                        onOpen(true)
                    }
                    .padding(16.dp, 0.dp),
                painter = painterResource(id = R.drawable.ic_dropdown),
                contentDescription = "드롭다운 아이콘")
            DropdownMenu(
                modifier = Modifier
                    .background(White)
                    .width(260.dp)
                    .border(
                        width = 1.dp,
                        color = Purple,
                        shape = RoundedCornerShape(14.dp)
                    )
                    .align(Alignment.CenterEnd),
                expanded = requestToOpen,
                onDismissRequest = { onOpen(false) },
                offset = DpOffset(0.dp, 24.dp)
            ) {
                for (i in valueList.indices) {
                    val value = valueList[i]
                    val text = textList[i]
                    DropdownMenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            onOpen(false)
                            onTextChanged(value, text)
                        }
                    ) {
                        Text(text, modifier = Modifier.wrapContentWidth())
                    }
                }

                DropdownMenuItem(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { }
                ) {
                    BasicTextField(
                        modifier = Modifier.weight(1f),
                        value = addText,
                        onValueChange = { addText = it },
                        singleLine = true,
                        textStyle = TextStyle(color = Purple),
                        decorationBox = { innerTextField ->
                            Row(modifier = Modifier.fillMaxWidth()) {
                                if (addText.isEmpty()) {
                                    Text(
                                        text = "추가하기",
                                        color = Purple)
                                }
                            }
                            innerTextField()
                        })
                    IconButton(
                        modifier = Modifier.then(Modifier
                            .size(14.dp)),
                        onClick = {
                            onAddClick(addText)
                            addText = ""
                        },
                        enabled = addText.isNotBlank()
                    ) {
                        Icon(painter = painterResource(R.drawable.ic_plus), contentDescription = "추가하기", tint = Purple)
                    }
                }
            }
        }
    }
    Divider(color = Purple04, thickness = 1.dp, modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 8.dp))
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CheckableItem(
    modifier: Modifier = Modifier,
    isSelectMode: Boolean,
    onPress: () -> Unit,
    onLongPress: () -> Unit,
    onUpdateClick: () -> Unit,
    Composition: @Composable () -> Unit
) {
    val (isChecked, setIsChecked) = remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .background(if (isChecked) White else OffWhite)
            .combinedClickable(
                onClick = {
                    if (isSelectMode) {
                        if (isChecked) {
                            onPress()
                            setIsChecked(false)
                        } else {
                            onLongPress()
                            setIsChecked(true)
                        }
                    } else {
                        onUpdateClick()
                    }
                },
                onLongClick = {
                    setIsChecked(true)
                    onLongPress()
                },
            )
    ) {
        if (isSelectMode)
            Icon(
                painter =
                if (isChecked) painterResource(id = R.drawable.ic_checkedbox)
                else painterResource(id = R.drawable.ic_checkbox),
                contentDescription = "체크 박스",
                tint= Color.Unspecified)
        else setIsChecked(false)

        Composition()
    }
}