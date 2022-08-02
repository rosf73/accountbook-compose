package com.woowacamp.android_accountbook_15.data.utils

import com.woowacamp.android_accountbook_15.data.model.Category
import com.woowacamp.android_accountbook_15.data.model.History
import com.woowacamp.android_accountbook_15.data.model.PaymentMethod

interface AccountBookDataSource {

    /** Create */
    fun createHistory(
        type: Int,
        content: String? = null,
        date: String,
        amount: Int,
        paymentId: Long? = null,
        categoryId: Long? = null): Long

    fun createPaymentMethod(
        name: String
    ): Long

    fun createCategory(
        type: Int,
        name: String,
        color: Long
    ): Long

    /** Read */
    fun readMonthlyHistories(
        year: Int,
        month: Int
    ): Map<String, List<History>>

    fun readAllPaymentMethod(): List<PaymentMethod>

    fun readAllExpensesCategory(): List<Category>

    fun readAllIncomeCategory(): List<Category>

    /** Update */
    fun updateHistory(
        id: Long,
        type: Int,
        content: String? = null,
        amount: Int? = null,
        date: String? = null,
        paymentMethod: PaymentMethod? = null,
        category: Category? = null
    ): Int

    fun updatePaymentMethod(
        id: Long,
        name: String
    ): Int

    fun updateCategory(
        id: Long,
        name: String,
        color: Long
    ): Int

    /** Delete */
    fun deleteHistories(
        ids: List<Long>
    ): Int

    fun deletePaymentMethods(
        ids: List<Long>
    ): Int

    fun deleteCategories(
        ids: List<Long>
    ): Int
}