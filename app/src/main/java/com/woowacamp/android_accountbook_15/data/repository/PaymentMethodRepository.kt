package com.woowacamp.android_accountbook_15.data.repository

import android.content.Context
import com.woowacamp.android_accountbook_15.data.dao.PaymentMethodDAO
import com.woowacamp.android_accountbook_15.data.model.PaymentMethod
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class PaymentMethodRepository @Inject constructor (
    @ActivityContext context: Context
) {

    private val dao = PaymentMethodDAO(context)

    suspend fun createPaymentMethod(name: String): Boolean {
        dao.insertPaymentMethod(name)
        return true
    }

    suspend fun readAllPaymentMethods(): List<PaymentMethod> = dao.readAllPaymentMethod()
}