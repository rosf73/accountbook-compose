package com.woowacamp.android_accountbook_15.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.woowacamp.android_accountbook_15.data.utils.*
import com.woowacamp.android_accountbook_15.utils.DATABASE_NAME
import com.woowacamp.android_accountbook_15.utils.DATABASE_VERSION
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class AccountBookHelper @Inject constructor (
    @ActivityContext context: Context
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.apply {
            if (isReadOnly)
                execSQL(SQL_FOREIGN_SET)
            execSQL(SQL_CREATE_HISTORY)
            execSQL(SQL_CREATE_PAYMENT_METHOD)
            execSQL(SQL_CREATE_CATEGORY)

            /* 기본 데이터 세팅 */
            execSQL(SQL_INSERT_PAYMENT_METHOD_1)
            execSQL(SQL_INSERT_PAYMENT_METHOD_2)
            execSQL(SQL_INSERT_EXPENSES_CATEGORY_1)
            execSQL(SQL_INSERT_EXPENSES_CATEGORY_2)
            execSQL(SQL_INSERT_EXPENSES_CATEGORY_3)
            execSQL(SQL_INSERT_EXPENSES_CATEGORY_4)
            execSQL(SQL_INSERT_EXPENSES_CATEGORY_5)
            execSQL(SQL_INSERT_EXPENSES_CATEGORY_6)
            execSQL(SQL_INSERT_EXPENSES_CATEGORY_7)
            execSQL(SQL_INSERT_INCOME_CATEGORY_1)
            execSQL(SQL_INSERT_INCOME_CATEGORY_2)
            execSQL(SQL_INSERT_INCOME_CATEGORY_3)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.apply {
            execSQL(SQL_DELETE_HISTORY)
            execSQL(SQL_DELETE_PAYMENT_METHOD)
            execSQL(SQL_DELETE_CATEGORY)
        }
        onCreate(db)
    }
}