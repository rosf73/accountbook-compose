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