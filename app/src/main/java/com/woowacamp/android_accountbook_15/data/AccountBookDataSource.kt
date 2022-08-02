package com.woowacamp.android_accountbook_15.data

import android.content.ContentValues
import android.provider.BaseColumns
import com.woowacamp.android_accountbook_15.data.model.Category
import com.woowacamp.android_accountbook_15.data.model.History
import com.woowacamp.android_accountbook_15.data.model.PaymentMethod
import com.woowacamp.android_accountbook_15.data.utils.*
import com.woowacamp.android_accountbook_15.utils.getMonthAndYearHyphen
import javax.inject.Inject

class AccountBookDataSource @Inject constructor(
    dbHelper: AccountBookHelper
) {
    
    private val writableDB = dbHelper.writableDatabase
    private val readableDB = dbHelper.readableDatabase
    
    fun addHistory(
        type: Int,
        content: String? = null,
        date: String,
        amount: Int,
        paymentId: Long? = null,
        categoryId: Long? = null
    ): Long {
        return writableDB.run {
            val values = ContentValues().apply {
                put(HistoryColumns.COLUMN_NAME_TYPE, type)
                content?.let { put(HistoryColumns.COLUMN_NAME_CONTENT, content) }
                put(HistoryColumns.COLUMN_NAME_DATE, date)
                put(HistoryColumns.COLUMN_NAME_AMOUNT, amount)
                paymentId?.let { put(HistoryColumns.COLUMN_NAME_PAYMENT_ID, paymentId) }
                categoryId?.let { put(HistoryColumns.COLUMN_NAME_CATEGORY_ID, categoryId) }
            }

            insert(HistoryColumns.TABLE_NAME, null, values)
        }
    }

    fun removeHistory(
        id: Long
    ): Int {
        return writableDB.run {
            val selection = "${BaseColumns._ID} LIKE ?"
            val selectionArgs = arrayOf(id.toString())

            delete(HistoryColumns.TABLE_NAME, selection, selectionArgs)
        }
    }

    fun updateHistory(
        id: Long,
        type: Int,
        content: String? = null,
        amount: Int? = null,
        date: String? = null,
        paymentMethod: PaymentMethod? = null,
        category: Category? = null
    ): Int {
        return writableDB.run {
            val values = ContentValues().apply {
                put(HistoryColumns.COLUMN_NAME_TYPE, type)
                content?.let { put(HistoryColumns.COLUMN_NAME_CONTENT, content) }
                amount?.let { put(HistoryColumns.COLUMN_NAME_AMOUNT, amount) }
                date?.let { put(HistoryColumns.COLUMN_NAME_DATE, date) }
                paymentMethod?.let { put(HistoryColumns.COLUMN_NAME_PAYMENT_ID, paymentMethod.id) }
                category?.let { put(HistoryColumns.COLUMN_NAME_CATEGORY_ID, category.id) }
            }

            val selection = "${BaseColumns._ID} LIKE ?"
            val selectionArgs = arrayOf(id.toString())

            update(
                HistoryColumns.TABLE_NAME,
                values,
                selection,
                selectionArgs
            )
        }
    }

    fun getAllHistory(
        year: Int,
        month: Int
    ): Map<String, List<History>>
        = readableDB.run {
            val cursor = rawQuery(SQL_SELECT_ALL_HISTORY, arrayOf(getMonthAndYearHyphen(year, month)))

            mutableMapOf<String, MutableList<History>>().apply {
                with(cursor) {
                    while (moveToNext()) {
                        val id = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                        val type = getInt(getColumnIndexOrThrow(HistoryColumns.COLUMN_NAME_TYPE))
                        val content = getString(getColumnIndexOrThrow(HistoryColumns.COLUMN_NAME_CONTENT))
                        val date = getString(getColumnIndexOrThrow(HistoryColumns.COLUMN_NAME_DATE))
                        val amount = getInt(getColumnIndexOrThrow(HistoryColumns.COLUMN_NAME_AMOUNT))
                        val paymentId = getLong(getColumnIndexOrThrow("payment_id"))
                        val paymentName = getString(getColumnIndexOrThrow("payment_name"))
                        val categoryId = getLong(getColumnIndexOrThrow("category_id"))
                        val categoryType = getInt(getColumnIndexOrThrow("category_type"))
                        val categoryName = getString(getColumnIndexOrThrow("category_name"))
                        val categoryColor = getLong(getColumnIndexOrThrow("category_color"))
                        val history = History(
                                id, type, content, date, amount,
                                if (type == 1) null else PaymentMethod(paymentId, paymentName),
                                if (categoryName == null) null else Category(categoryId, categoryType, categoryName, categoryColor)
                            )
                        val monthDay = history.date.substring(5)
                        if (containsKey(monthDay)) {
                            get(monthDay)?.add(history)
                        } else {
                            put(monthDay, mutableListOf(history))
                        }
                    }
                }
                cursor.close()
            }
        }
    
    fun addPaymentMethod(
        name: String
    ): Long 
        = writableDB.run {
            val values = ContentValues().apply {
                put(PaymentMethodColumns.COLUMN_NAME_NAME, name)
            }

            insertOrThrow(PaymentMethodColumns.TABLE_NAME, null, values)
        }

    fun updatePaymentMethod(
        id: Long,
        name: String
    ): Int {
        return writableDB.run {
            val values = ContentValues().apply {
                put(PaymentMethodColumns.COLUMN_NAME_NAME, name)
            }

            val selection = "${BaseColumns._ID} LIKE ?"
            val selectionArgs = arrayOf(id.toString())

            update(
                PaymentMethodColumns.TABLE_NAME,
                values,
                selection,
                selectionArgs
            )
        }
    }
    
    fun getAllPaymentMethod(): List<PaymentMethod> 
        = readableDB.run {
            val cursor = query(
                PaymentMethodColumns.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
            )

            val payments = mutableListOf<PaymentMethod>()
            with(cursor) {
                while (moveToNext()) {
                    val id = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                    val name = getString(getColumnIndexOrThrow(PaymentMethodColumns.COLUMN_NAME_NAME))
                    payments.add(
                        PaymentMethod(id, name)
                    )
                }
            }
            cursor.close()

            payments
        }
    
    fun addCategory(
        type: Int,
        name: String,
        color: Long
    ): Long
        = writableDB.run {
            val values = ContentValues().apply {
                put(CategoryColumns.COLUMN_NAME_TYPE, type)
                put(CategoryColumns.COLUMN_NAME_NAME, name)
                put(CategoryColumns.COLUMN_NAME_COLOR, color)
            }
    
            insertOrThrow(CategoryColumns.TABLE_NAME, null, values)
        }

    fun updateCategory(
        id: Long,
        name: String,
        color: Long
    ): Int {
        return writableDB.run {
            val values = ContentValues().apply {
                put(CategoryColumns.COLUMN_NAME_NAME, name)
                put(CategoryColumns.COLUMN_NAME_COLOR, color)
            }

            val selection = "${BaseColumns._ID} LIKE ?"
            val selectionArgs = arrayOf(id.toString())

            update(
                CategoryColumns.TABLE_NAME,
                values,
                selection,
                selectionArgs
            )
        }
    }

    fun getAllExpensesCategory(): List<Category>
        = readableDB.run {
            val cursor = query(
                CategoryColumns.TABLE_NAME,
                null,
                "${CategoryColumns.COLUMN_NAME_TYPE} = ?",
                arrayOf("0"),
                null,
                null,
                null
            )

            val categories = mutableListOf<Category>()
            with(cursor) {
                while (moveToNext()) {
                    val id = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                    val type = getInt(getColumnIndexOrThrow(CategoryColumns.COLUMN_NAME_TYPE))
                    val name = getString(getColumnIndexOrThrow(CategoryColumns.COLUMN_NAME_NAME))
                    val color = getLong(getColumnIndexOrThrow(CategoryColumns.COLUMN_NAME_COLOR))
                    categories.add(
                        Category(id, type, name, color)
                    )
                }
            }
            cursor.close()

            categories
        }

    fun getAllIncomeCategory(): List<Category>
            = readableDB.run {
            val cursor = query(
                CategoryColumns.TABLE_NAME,
                null,
                "${CategoryColumns.COLUMN_NAME_TYPE} = ?",
                arrayOf("1"),
                null,
                null,
                null
            )

            val categories = mutableListOf<Category>()
            with(cursor) {
                while (moveToNext()) {
                    val id = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                    val type = getInt(getColumnIndexOrThrow(CategoryColumns.COLUMN_NAME_TYPE))
                    val name = getString(getColumnIndexOrThrow(CategoryColumns.COLUMN_NAME_NAME))
                    val color = getLong(getColumnIndexOrThrow(CategoryColumns.COLUMN_NAME_COLOR))
                    categories.add(
                        Category(id, type, name, color)
                    )
                }
            }
            cursor.close()

            categories
        }
}