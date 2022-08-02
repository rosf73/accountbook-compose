package com.woowacamp.android_accountbook_15.utils

import java.lang.IllegalArgumentException
import java.text.SimpleDateFormat
import java.util.*


/**
 * 단순 형태 변환
 */
fun getMonthAndYearKorean(year: Int, month: Int): String = "${year}년 ${if (month < 10) "0$month" else month}월"

fun getMonthAndYearHyphen(year: Int, month: Int): String = "$year-${if (month < 10) "0$month" else month}"

/**
 * 오늘 날짜 얻기
 */
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

    val day = getDay(Calendar.getInstance())
    return SimpleDateFormat("yyyy. MM. dd ${day}요일", Locale.KOREA).format(today)
}

/**
 * 요일 얻기
 */
fun getDayKorean(year: Int, month: Int, date: Int): String {
    val cal = Calendar.getInstance()
    cal.set(year, month-1, date)

    val day = getDay(cal)
    return SimpleDateFormat("yyyy. MM. dd ${day}요일", Locale.KOREA).format(cal.time)
}

fun getDayKoreanWithoutYear(year: Int, month: Int, date: Int): String {
    val cal = Calendar.getInstance()
    cal.set(year, month-1, date)

    val day = getDay(cal)
    return SimpleDateFormat("MM월 dd일 $day", Locale.KOREA).format(cal.time)
}

fun getDaysInMonth(year: Int, month: Int): Int
    = when (month-1) {
        Calendar.JANUARY, Calendar.MARCH, Calendar.MAY, Calendar.JULY, Calendar.AUGUST, Calendar.OCTOBER, Calendar.DECEMBER -> 31
        Calendar.APRIL, Calendar.JUNE, Calendar.SEPTEMBER, Calendar.NOVEMBER -> 30
        Calendar.FEBRUARY -> if (year % 4 == 0 && year % 100 != 0 && year % 400 == 0) 29 else 28
        else -> throw IllegalArgumentException("Invalid Month")
    }

fun changeKoreanToHyphen(dayKorean: String): String {
    val splitDate = dayKorean.split(". ")

    return "${splitDate[0]}-${splitDate[1]}-${splitDate[2].split(" ")[0]}"
}

fun changeHyphenToKorean(dayHyphen: String): String {
    val splitDate = dayHyphen.split("-").map { it.toInt() }

    return getDayKorean(splitDate[0], splitDate[1], splitDate[2])
}

private fun getDay(calendar: Calendar): String
        = when (calendar.get(Calendar.DAY_OF_WEEK)) {
    1 -> "일"
    2 -> "월"
    3 -> "화"
    4 -> "수"
    5 -> "목"
    6 -> "금"
    else -> "토"
}