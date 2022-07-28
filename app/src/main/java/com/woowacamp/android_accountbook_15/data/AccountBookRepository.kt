package com.woowacamp.android_accountbook_15.data

import com.woowacamp.android_accountbook_15.data.model.History
import com.woowacamp.android_accountbook_15.data.model.PaymentMethod
import javax.inject.Inject

class AccountBookRepository @Inject constructor(
    private val dataSource: AccountBookDataSource
) {

    fun getMonthlyHistories(year: Int, month: Int): Result<List<History>> {
        return runCatching {
            dataSource.getAllHistory(year, month)
        }
    }

    fun getAllPaymentMethod(): Result<List<PaymentMethod>>
        = runCatching { dataSource.getAllPaymentMethod() }

    fun insertPaymentMethod(name: String): Result<Long>
        = runCatching { dataSource.addPaymentMethod(name) }
}