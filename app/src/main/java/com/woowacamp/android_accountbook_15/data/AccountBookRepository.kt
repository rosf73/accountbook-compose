package com.woowacamp.android_accountbook_15.data

import com.woowacamp.android_accountbook_15.data.model.Category
import com.woowacamp.android_accountbook_15.data.model.History
import com.woowacamp.android_accountbook_15.data.model.PaymentMethod
import javax.inject.Inject

class AccountBookRepository @Inject constructor(
    private val dataSource: AccountBookDataSource
) {

    fun readMonthlyHistories(year: Int, month: Int): Result<Map<String, List<History>>>
        = runCatching { dataSource.readMonthlyHistories(year, month) }

    fun readHistoriesEachCategory(year: Int, month: Int, categoryId: Long): Result<Map<String, List<History>>>
        = runCatching { dataSource.readHistoriesEachCategory(year, month, categoryId) }

    fun readMonthlyTotalAmount(
        startYear: Int,
        startMonth: Int,
        endYear: Int,
        endMonth: Int,
        categoryId: Long
    ): Result<List<Pair<Int, Int>>>
        = runCatching { dataSource.readMonthlyTotalAmount(startYear, startMonth, endYear, endMonth, categoryId) }

    fun readAllPaymentMethod(): Result<List<PaymentMethod>>
        = runCatching { dataSource.readAllPaymentMethod() }

    fun readAllExpensesCategory(): Result<List<Category>>
        = runCatching { dataSource.readAllExpensesCategory() }

    fun readAllIncomeCategory(): Result<List<Category>>
        = runCatching { dataSource.readAllIncomeCategory() }

    fun insertHistory(
        type: Int,
        content: String? = null,
        date: String,
        amount: Int,
        paymentId: Long? = null,
        categoryId: Long
    ): Result<Long>
        = runCatching { dataSource.createHistory(type, content, date, amount, paymentId, categoryId) }

    fun createPaymentMethod(name: String): Result<Long>
        = runCatching { dataSource.createPaymentMethod(name) }

    fun createCategory(type: Int, name: String, color: Long): Result<Long>
        = runCatching { dataSource.createCategory(type, name, color) }

    fun updateHistory(
        id: Long,
        type: Int,
        content: String? = null,
        amount: Int? = null,
        date: String? = null,
        paymentMethod: PaymentMethod? = null,
        category: Category? = null
    ): Result<Int>
        = runCatching { dataSource.updateHistory(id, type, content, amount, date, paymentMethod, category) }

    fun updatePaymentMethod(id: Long, name: String): Result<Int>
        = runCatching { dataSource.updatePaymentMethod(id, name) }

    fun updateCategory(id: Long, name: String, color: Long): Result<Int>
        = runCatching { dataSource.updateCategory(id, name, color) }

    fun deleteHistories(ids: List<Long>)
        = runCatching { dataSource.deleteHistories(ids) }
}