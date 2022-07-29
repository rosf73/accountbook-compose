package com.woowacamp.android_accountbook_15.utils

import android.widget.Toast
import com.woowacamp.android_accountbook_15.AccountBookApplication

fun createToast(text: String) {
    Toast.makeText(
        AccountBookApplication.instance,
        text,
        Toast.LENGTH_LONG
    ).show()
}