package com.woowacamp.android_accountbook_15.data.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.woowacamp.android_accountbook_15.data.model.Category
import com.woowacamp.android_accountbook_15.data.model.History
import com.woowacamp.android_accountbook_15.data.model.PaymentMethod
import com.woowacamp.android_accountbook_15.utils.DATABASE_NAME
import com.woowacamp.android_accountbook_15.utils.DATABASE_VERSION
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


private val columns = AccountBookContract.HistoryColumns
private val paymentMethodColumns = AccountBookContract.PaymentMethodColumns
private val categoryColumns = AccountBookContract.CategoryColumns

private const val SQL_CREATE_HISTORY =
    "CREATE TABLE ${columns.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${columns.COLUMN_NAME_TYPE} INTEGER," +
            "${columns.COLUMN_NAME_CONTENT} TEXT," +
            "${columns.COLUMN_NAME_DATE} DATETIME," +
            "${columns.COLUMN_NAME_AMOUNT} INTEGER," +
            "${columns.COLUMN_NAME_PAYMENT_ID} INTEGER," +
            "${columns.COLUMN_NAME_CATEGORY_ID} INTEGER," +
            "FOREIGN KEY(${columns.COLUMN_NAME_PAYMENT_ID})" +
            "REFERENCES ${paymentMethodColumns.TABLE_NAME} (${BaseColumns._ID})," +
            "FOREIGN KEY(${columns.COLUMN_NAME_PAYMENT_ID})" +
            "REFERENCES ${categoryColumns.TABLE_NAME} (${BaseColumns._ID})" +
            ")"

private const val SQL_DELETE_HISTORY = "DROP TABLE IF EXISTS ${columns.TABLE_NAME}"

class HistoryDAO @Inject constructor (
    @ActivityContext context: Context
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        if (!db.isReadOnly) {
            db.execSQL("PRAGMA foreign_keys=ON")
        }
        db.execSQL(SQL_CREATE_HISTORY)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_HISTORY)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    suspend fun insertHistory(
        type: Int,
        content: String,
        date: String,
        amount: Int,
        paymentMethod: PaymentMethod,
        category: Category
    ) {
        withContext(Dispatchers.IO) {
            val db = writableDatabase
            val values = ContentValues().apply {
                put(columns.COLUMN_NAME_TYPE, type)
                put(columns.COLUMN_NAME_CONTENT, content)
                put(columns.COLUMN_NAME_DATE, date)
                put(columns.COLUMN_NAME_AMOUNT, amount)
                put(columns.COLUMN_NAME_PAYMENT_ID, paymentMethod.id)
                put(columns.COLUMN_NAME_CATEGORY_ID, category.id)
            }

            db?.insert(columns.TABLE_NAME, null, values)
        }
    }

    suspend fun deleteHistory(
        id: Long,
    ): Int {
        return withContext(Dispatchers.IO) {
            val db = writableDatabase
            val selection = "${BaseColumns._ID} LIKE ?"
            val selectionArgs = arrayOf(id.toString())
            val deletedRows = db.delete(columns.TABLE_NAME, selection, selectionArgs)

            deletedRows
        }
    }

    suspend fun updateHistory(
        id: Long,
        content: String? = null,
        amount: Int? = null,
        date: String? = null,
        paymentMethod: PaymentMethod? = null,
        category: Category? = null
    ): Int {
        return withContext(Dispatchers.IO) {
            val db = writableDatabase

            val values = ContentValues().apply {
                content?.let { put(columns.COLUMN_NAME_CONTENT, content) }
                amount?.let { put(columns.COLUMN_NAME_AMOUNT, amount) }
                date?.let { put(columns.COLUMN_NAME_DATE, date) }
                paymentMethod?.let { put(columns.COLUMN_NAME_PAYMENT_ID, paymentMethod.id) }
                category?.let { put(columns.COLUMN_NAME_CATEGORY_ID, category.id) }
            }

            val selection = "${BaseColumns._ID} LIKE ?"
            val selectionArgs = arrayOf(id.toString())
            db.update(
                columns.TABLE_NAME,
                values,
                selection,
                selectionArgs
            )
        }
    }

    suspend fun readAllHistory(): List<History> {
        return withContext(Dispatchers.IO) {
            val db = readableDatabase

            val cursor = db.rawQuery(
                "SELECT " +
                        "${columns.TABLE_NAME}.${BaseColumns._ID}," +
                        "${columns.TABLE_NAME}.${columns.COLUMN_NAME_TYPE}," +
                        "${columns.TABLE_NAME}.${columns.COLUMN_NAME_CONTENT}," +
                        "${columns.TABLE_NAME}.${columns.COLUMN_NAME_DATE}," +
                        "${columns.TABLE_NAME}.${columns.COLUMN_NAME_AMOUNT}," +
                        "${paymentMethodColumns.TABLE_NAME}.${BaseColumns._ID} AS payment_id," +
                        "${paymentMethodColumns.TABLE_NAME}.${paymentMethodColumns.COLUMN_NAME_NAME} AS payment_name," +
                        "${categoryColumns.TABLE_NAME}.${BaseColumns._ID} AS category_id," +
                        "${categoryColumns.TABLE_NAME}.${categoryColumns.COLUMN_NAME_TYPE} AS category_type," +
                        "${categoryColumns.TABLE_NAME}.${categoryColumns.COLUMN_NAME_NAME} AS category_name," +
                        "${categoryColumns.TABLE_NAME}.${categoryColumns.COLUMN_NAME_COLOR} AS category_color" +
                        " FROM ${columns.TABLE_NAME}" +
                        " LEFT JOIN ${paymentMethodColumns.TABLE_NAME}" +
                        " ON ${columns.TABLE_NAME}.${BaseColumns._ID} = ${paymentMethodColumns.TABLE_NAME}.${BaseColumns._ID}" +
                        " LEFT JOIN ${categoryColumns.TABLE_NAME}" +
                        " ON ${columns.TABLE_NAME}.${BaseColumns._ID} = ${categoryColumns.TABLE_NAME}.${BaseColumns._ID}",
                null
            )

            val histories = mutableListOf<History>()
            with(cursor) {
                while (moveToNext()) {
                    val id = getLong(getColumnIndexOrThrow("id"))
                    val type = getInt(getColumnIndexOrThrow(columns.COLUMN_NAME_TYPE))
                    val content = getString(getColumnIndexOrThrow(columns.COLUMN_NAME_CONTENT))
                    val date = getString(getColumnIndexOrThrow(columns.COLUMN_NAME_DATE))
                    val amount = getInt(getColumnIndexOrThrow(columns.COLUMN_NAME_AMOUNT))
                    val paymentId = getLong(getColumnIndexOrThrow("payment_id"))
                    val paymentName = getString(getColumnIndexOrThrow("payment_name"))
                    val categoryId = getLong(getColumnIndexOrThrow("category_id"))
                    val categoryType = getInt(getColumnIndexOrThrow("category_type"))
                    val categoryName = getString(getColumnIndexOrThrow("category_name"))
                    val categoryColor = getString(getColumnIndexOrThrow("category_color"))
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
    }
}