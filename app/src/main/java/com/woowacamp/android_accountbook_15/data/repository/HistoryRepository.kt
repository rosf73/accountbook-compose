package com.woowacamp.android_accountbook_15.data.repository

import android.content.Context
import com.woowacamp.android_accountbook_15.data.dao.HistoryDAO
import com.woowacamp.android_accountbook_15.data.model.Category
import com.woowacamp.android_accountbook_15.data.model.PaymentMethod
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class HistoryRepository @Inject constructor (
    @ActivityContext context: Context
) {

    private val dao = HistoryDAO(context)

    suspend fun createHistory(
        type: Int,
        content: String,
        date: String,
        amount: Int,
        paymentMethod: PaymentMethod,
        category: Category
    ): Boolean {
        dao.insertHistory(type, content, date, amount, paymentMethod, category)
        return true
    }

    suspend fun deleteHistory(id: Long): Int {
        return dao.deleteHistory(id)
    }

    suspend fun updateHistory(
        id: Long,
        content: String? = null,
        amount: Int? = null,
        date: String? = null,
        paymentMethod: PaymentMethod? = null,
        category: Category? = null
    ): Int {
        return dao.updateHistory(id, content, amount, date, paymentMethod, category)
    }

    suspend fun readAllHistories() = dao.readAllHistories()
}