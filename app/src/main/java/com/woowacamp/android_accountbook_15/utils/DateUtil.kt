package com.woowacamp.android_accountbook_15.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun getTodayMonthAndYear(): String {
    val today = Calendar.getInstance().time
    return SimpleDateFormat("yyyy년 MM월", Locale.KOREA).format(today)
}

fun getTodayMonthAndYear(year: Int, month: Int): String {
    return "${year}년 ${month}월"
}

fun getTodayMonth(): Int {
    val today = Calendar.getInstance().time
    return SimpleDateFormat("MM", Locale.KOREA).format(today).toInt()
}

fun getTodayYear(): Int {
    val today = Calendar.getInstance().time
    return SimpleDateFormat("yyyy", Locale.KOREA).format(today).toInt()
}