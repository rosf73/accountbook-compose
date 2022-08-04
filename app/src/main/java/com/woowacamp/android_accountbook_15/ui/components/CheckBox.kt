package com.woowacamp.android_accountbook_15.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.woowacamp.android_accountbook_15.R
import com.woowacamp.android_accountbook_15.ui.theme.LightPurple
import com.woowacamp.android_accountbook_15.ui.theme.White

@Composable
fun PurpleCheckBox(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .size(16.dp)
            .background(if (checked) White else LightPurple)
            .border(width = 1.dp, color = White, shape = RoundedCornerShape(4.dp)),
        contentAlignment = Alignment.Center
    ) {
        if (checked)
            Icon(
                modifier = Modifier.size(12.dp, 7.dp),
                painter = painterResource(R.drawable.ic_check),
                contentDescription = "체크")
    }
}