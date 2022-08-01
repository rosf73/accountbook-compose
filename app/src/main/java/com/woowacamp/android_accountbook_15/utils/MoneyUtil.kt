package com.woowacamp.android_accountbook_15.utils

import java.text.DecimalFormat


fun Int.toMoneyString(): String = DecimalFormat("#,###").format(this)

fun String.toMoneyInt(): Int = this.replace(",","").toInt()