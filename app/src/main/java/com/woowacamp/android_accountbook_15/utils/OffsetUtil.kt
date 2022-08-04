package com.woowacamp.android_accountbook_15.utils

import android.graphics.Rect
import androidx.compose.ui.graphics.NativePaint

fun getRect(paint: NativePaint, text: String): Pair<Int, Int> {
    val rect = Rect() // text 의 크기
    paint.getTextBounds(text, 0, text.length, rect)

    return Pair(rect.width(), rect.height())
}