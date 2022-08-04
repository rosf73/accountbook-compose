package com.woowacamp.android_accountbook_15.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.woowacamp.android_accountbook_15.ui.theme.White

@Composable
fun CategoryView(
    modifier: Modifier = Modifier,
    color: Long,
    name: String
) {
    Text(modifier = modifier
        .width(56.dp)
        .clip(RoundedCornerShape(14.dp))
        .background(Color(color))
        .padding(0.dp, 4.dp),
        text = name,
        color = White,
        fontSize = 10.sp,
        textAlign = TextAlign.Center)
}