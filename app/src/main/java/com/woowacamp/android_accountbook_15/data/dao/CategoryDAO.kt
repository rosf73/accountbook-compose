package com.woowacamp.android_accountbook_15.data.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.woowacamp.android_accountbook_15.utils.DATABASE_NAME
import com.woowacamp.android_accountbook_15.utils.DATABASE_VERSION

class CategoryDAO(context: Context)
    : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(p0: SQLiteDatabase?) {
        TODO("Not yet implemented")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}