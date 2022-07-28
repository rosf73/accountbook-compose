package com.woowacamp.android_accountbook_15.ui.components

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.woowacamp.android_accountbook_15.R
import com.woowacamp.android_accountbook_15.ui.theme.Yellow

@Composable
fun FloatingButton(
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
        backgroundColor = Yellow
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_plus),
            contentDescription = ""
        )
    }
}