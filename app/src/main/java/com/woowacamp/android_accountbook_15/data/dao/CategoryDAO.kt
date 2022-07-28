package com.woowacamp.android_accountbook_15.data.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.woowacamp.android_accountbook_15.data.model.Category
import com.woowacamp.android_accountbook_15.data.model.PaymentMethod
import com.woowacamp.android_accountbook_15.utils.DATABASE_NAME
import com.woowacamp.android_accountbook_15.utils.DATABASE_VERSION
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


private val columns = AccountBookContract.CategoryColumns

private const val SQL_CREATE_CATEGORY =
    "CREATE TABLE ${columns.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${columns.COLUMN_NAME_TYPE} INTEGER," +
            "${columns.COLUMN_NAME_NAME} TEXT," +
            "${columns.COLUMN_NAME_COLOR} TEXT)"

private const val SQL_DELETE_CATEGORY = "DROP TABLE IF EXISTS ${columns.TABLE_NAME}"

class CategoryDAO @Inject constructor (
    @ActivityContext context: Context
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_CATEGORY)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_CATEGORY)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    suspend fun insertCategory(type: Int, name: String, color: String) {
        withContext(Dispatchers.IO) {
            val db = writableDatabase
            val values = ContentValues().apply {
                put(columns.COLUMN_NAME_TYPE, type)
                put(columns.COLUMN_NAME_NAME, name)
                put(columns.COLUMN_NAME_COLOR, color)
            }

            db?.insert(columns.TABLE_NAME, null, values)
        }
    }

    suspend fun readAllCategory(): List<Category> {
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

            val categories = mutableListOf<Category>()
            with(cursor) {
                while (moveToNext()) {
                    val id = getLong(getColumnIndexOrThrow("id"))
                    val type = getInt(getColumnIndexOrThrow(columns.COLUMN_NAME_TYPE))
                    val name = getString(getColumnIndexOrThrow(columns.COLUMN_NAME_NAME))
                    val color = getString(getColumnIndexOrThrow(columns.COLUMN_NAME_COLOR))
                    categories.add(
                        Category(id, type, name, color)
                    )
                }
            }
            cursor.close()

            categories
        }
    }
}