package com.woowacamp.android_accountbook_15.data

import android.content.ContentValues
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.woowacamp.android_accountbook_15.data.utils.HistoryColumns
import com.woowacamp.android_accountbook_15.data.model.Category
import com.woowacamp.android_accountbook_15.data.model.History
import com.woowacamp.android_accountbook_15.data.model.PaymentMethod
import com.woowacamp.android_accountbook_15.data.utils.CategoryColumns
import com.woowacamp.android_accountbook_15.data.utils.PaymentMethodColumns
import com.woowacamp.android_accountbook_15.data.utils.SQL_SELECT_ALL_HISTORY
import javax.inject.Inject

class AccountBookDataSource @Inject constructor(
    dbHelper: AccountBookHelper
) {
    
    private val writableDB = dbHelper.writableDatabase
    private val readableDB = dbHelper.readableDatabase
    
    fun addHistory(
        type: Int,
        content: String,
        date: String,
        amount: Int,
        paymentMethod: PaymentMethod,
        category: Category
    ): Long {
        return writableDB.run {
            val values = ContentValues().apply {
                put(HistoryColumns.COLUMN_NAME_TYPE, type)
                put(HistoryColumns.COLUMN_NAME_CONTENT, content)
                put(HistoryColumns.COLUMN_NAME_DATE, date)
                put(HistoryColumns.COLUMN_NAME_AMOUNT, amount)
                put(HistoryColumns.COLUMN_NAME_PAYMENT_ID, paymentMethod.id)
                put(HistoryColumns.COLUMN_NAME_CATEGORY_ID, category.id)
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
        content: String? = null,
        amount: Int? = null,
        date: String? = null,
        paymentMethod: PaymentMethod? = null,
        category: Category? = null
    ): Int {
        return writableDB.run {
            val values = ContentValues().apply {
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
    ): List<History> 
        = readableDB.run {
            val cursor = rawQuery(SQL_SELECT_ALL_HISTORY, null)

            val histories = mutableListOf<History>()
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
                    histories.add(
                        History(
                            id, type, content, date, amount,
                            PaymentMethod(paymentId, paymentName),
                            Category(categoryId, categoryType, categoryName, categoryColor)
                        )
                    )
                }
            }
            cursor.close()

            histories
        }
    
    fun addPaymentMethod(
        name: String
    ): Long 
        = writableDB.run {
            val values = ContentValues().apply {
                put(PaymentMethodColumns.COLUMN_NAME_NAME, name)
            }

            insert(PaymentMethodColumns.TABLE_NAME, null, values)
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
        color: String
    ): Long
        = writableDB.run {
            val values = ContentValues().apply {
                put(CategoryColumns.COLUMN_NAME_TYPE, type)
                put(CategoryColumns.COLUMN_NAME_NAME, name)
                put(CategoryColumns.COLUMN_NAME_COLOR, color)
            }
    
            insert(CategoryColumns.TABLE_NAME, null, values)
        }

    fun getAllCategory(): List<Category>
        = readableDB.run {
            val cursor = query(
                CategoryColumns.TABLE_NAME,
                null,
                null,
                null,
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