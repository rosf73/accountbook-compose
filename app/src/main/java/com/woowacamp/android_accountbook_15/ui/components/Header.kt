package com.woowacamp.android_accountbook_15.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.woowacamp.android_accountbook_15.ui.theme.Purple

@Composable
fun Header(
    modifier: Modifier = Modifier,
    title: String,
    leftIcon: Painter? = null,
    leftIconDescription: String = "",
    leftCallback: (() -> Unit)? = null,
    rightIcon: Painter? = null,
    rightIconDescription: String = "",
    rightCallback: (() -> Unit)? = null
) {
    Column {
        Row(
            modifier = modifier
                .padding(16.dp)
        ) {
            leftIcon?.let {
                IconButton(
                    modifier = Modifier.then(Modifier
                        .size(14.dp)
                        .align(Alignment.CenterVertically)),
                    onClick = { leftCallback?.let { it() } }
                ) {
                    Icon(painter = leftIcon, contentDescription = leftIconDescription)
                }
            }
            Text(
                text = title,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                modifier = modifier
                    .weight(1f)
            )
            rightIcon?.let {
                IconButton(
                    modifier = Modifier.then(Modifier
                        .size(14.dp)
                        .align(Alignment.CenterVertically)),
                    onClick = { rightCallback?.let { it() } }
                ) {
                    Icon(painter = rightIcon, contentDescription = rightIconDescription)
                }
            }
        }
        Divider(color = Purple, thickness = 1.dp)
    }
}