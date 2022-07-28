package com.woowacamp.android_accountbook_15.data.utils

import android.provider.BaseColumns

object HistoryColumns : BaseColumns {
    const val TABLE_NAME = "history"
    const val COLUMN_NAME_TYPE = "type"
    const val COLUMN_NAME_CONTENT = "content"
    const val COLUMN_NAME_DATE = "date"
    const val COLUMN_NAME_AMOUNT = "amount"
    const val COLUMN_NAME_PAYMENT_ID = "payment_id"
    const val COLUMN_NAME_CATEGORY_ID = "category_id"
}

object PaymentMethodColumns : BaseColumns {
    const val TABLE_NAME = "payment_method"
    const val COLUMN_NAME_NAME = "name"
}

object CategoryColumns : BaseColumns {
    const val TABLE_NAME = "category"
    const val COLUMN_NAME_TYPE = "type"
    const val COLUMN_NAME_NAME = "name"
    const val COLUMN_NAME_COLOR = "color"
}