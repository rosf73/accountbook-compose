package com.woowacamp.android_accountbook_15.data.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.woowacamp.android_accountbook_15.data.model.PaymentMethod
import com.woowacamp.android_accountbook_15.utils.DATABASE_NAME
import com.woowacamp.android_accountbook_15.utils.DATABASE_VERSION
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


private val columns = AccountBookContract.PaymentMethodColumns

private const val SQL_CREATE_PAYMENT_METHOD =
    "CREATE TABLE ${columns.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${columns.COLUMN_NAME_NAME} TEXT)"

private const val SQL_DELETE_PAYMENT_METHOD = "DROP TABLE IF EXISTS ${columns.TABLE_NAME}"


class PaymentMethodDAO @Inject constructor (
    @ActivityContext context: Context
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_PAYMENT_METHOD)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_PAYMENT_METHOD)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    suspend fun insertPaymentMethod(name: String) {
        withContext(Dispatchers.IO) {
            val db = writableDatabase
            val values = ContentValues().apply {
                put(columns.COLUMN_NAME_NAME, name)
            }

            db?.insert(columns.TABLE_NAME, null, values)
        }
    }

    suspend fun readAllPaymentMethod(): List<PaymentMethod> {
        return withContext(Dispatchers.IO) {
            val db = readableDatabase

            val cursor = db.query(
                columns.TABLE_NAME,
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
                    val id = getLong(getColumnIndexOrThrow("id"))
                    val name = getString(getColumnIndexOrThrow(columns.COLUMN_NAME_NAME))
                    payments.add(
                        PaymentMethod(id, name)
                    )
                }
            }
            cursor.close()

            payments
        }
    }
}