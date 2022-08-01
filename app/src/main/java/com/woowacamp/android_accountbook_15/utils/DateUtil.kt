package com.woowacamp.android_accountbook_15.utils

import java.text.SimpleDateFormat
import java.util.*

fun getMonthAndYearKorean(year: Int, month: Int): String = "${year}년 ${if (month < 10) "0$month" else month}월"

fun getMonthAndYearHyphen(year: Int, month: Int): String = "$year-${if (month < 10) "0$month" else month}"

fun getTodayMonth(): Int {
    val today = Calendar.getInstance().time
    return SimpleDateFormat("MM", Locale.KOREA).format(today).toInt()
}

fun getTodayYear(): Int {
    val today = Calendar.getInstance().time
    return SimpleDateFormat("yyyy", Locale.KOREA).format(today).toInt()
}

fun getTodayKorean(): String {
    val today = Calendar.getInstance().time

    var day = ""
    when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
        1 -> day = "일"
        2 -> day = "월"
        3 -> day = "화"
        4 -> day = "수"
        5 -> day = "목"
        6 -> day = "금"
        7 -> day = "토"
    }
    return SimpleDateFormat("yyyy. MM. dd ${day}요일", Locale.KOREA).format(today)
}

fun getDayKorean(year: Int, month: Int, date: Int): String {
    val cal = Calendar.getInstance()
    cal.set(year, month, date)

    var day = ""
    when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
        1 -> day = "일"
        2 -> day = "월"
        3 -> day = "화"
        4 -> day = "수"
        5 -> day = "목"
        6 -> day = "금"
        7 -> day = "토"
    }
    return SimpleDateFormat("yyyy. MM. dd ${day}요일", Locale.KOREA).format(cal.time)
}