package com.woowacamp.android_accountbook_15.data.model

data class History(
    val id: Long,
    val type: Int,
    val content: String? = null,
    val date: String,
    val amount: Int,
    val payment: PaymentMethod? = null,
    val category: Category
)
