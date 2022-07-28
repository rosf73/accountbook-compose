package com.woowacamp.android_accountbook_15.data

import com.woowacamp.android_accountbook_15.data.model.PaymentMethod
import javax.inject.Inject

class AccountBookRepository @Inject constructor(
    private val dataSource: AccountBookDataSource
) {

    fun getAllPaymentMethod(): Result<List<PaymentMethod>>
        = runCatching { dataSource.getAllPaymentMethod() }
}