package com.woowacamp.android_accountbook_15.data.model

data class History(
    val id: Long,
    val type: Int,
    val content: String,
    val date: String,
    val amount: Int,
    val payment: PaymentMethod,
    val category: Category
)
