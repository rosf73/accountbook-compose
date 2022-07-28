package com.woowacamp.android_accountbook_15.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun getTodayMonthAndYear(): String {
    val today = Calendar.getInstance().time
    return SimpleDateFormat("yyyy년 MM월", Locale.KOREA).format(today)
}