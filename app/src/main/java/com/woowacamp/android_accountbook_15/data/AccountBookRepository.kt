package com.woowacamp.android_accountbook_15.data

import com.woowacamp.android_accountbook_15.data.model.Category
import com.woowacamp.android_accountbook_15.data.model.History
import com.woowacamp.android_accountbook_15.data.model.PaymentMethod
import javax.inject.Inject

class AccountBookRepository @Inject constructor(
    private val dataSource: AccountBookDataSource
) {

    fun getMonthlyHistories(year: Int, month: Int): Result<Map<String, List<History>>>
        = runCatching { dataSource.getAllHistory(year, month) }

    fun getAllPaymentMethod(): Result<List<PaymentMethod>>
        = runCatching { dataSource.getAllPaymentMethod() }

    fun getAllExpensesCategory(): Result<List<Category>>
            = runCatching { dataSource.getAllExpensesCategory() }

    fun getAllIncomeCategory(): Result<List<Category>>
            = runCatching { dataSource.getAllIncomeCategory() }

    fun insertHistory(
        type: Int,
        content: String? = null,
        date: String,
        amount: Int,
        paymentId: Long? = null,
        categoryId: Long? = null
    ): Result<Long>
            = runCatching { dataSource.addHistory(type, content, date, amount, paymentId, categoryId) }

    fun insertPaymentMethod(name: String): Result<Long>
        = runCatching { dataSource.addPaymentMethod(name) }

    fun insertCategory(type: Int, name: String, color: Long): Result<Long>
        = runCatching { dataSource.addCategory(type, name, color) }

    fun updateHistory(
        id: Long,
        content: String? = null,
        amount: Int? = null,
        date: String? = null,
        paymentMethod: PaymentMethod? = null,
        category: Category? = null
    ): Result<Int>
            = runCatching { dataSource.updateHistory(id, content, amount, date, paymentMethod, category) }

    fun updatePaymentMethod(id: Long, name: String): Result<Int>
            = runCatching { dataSource.updatePaymentMethod(id, name) }

    fun updateCategory(id: Long, name: String, color: Long): Result<Int>
            = runCatching { dataSource.updateCategory(id, name, color) }
}