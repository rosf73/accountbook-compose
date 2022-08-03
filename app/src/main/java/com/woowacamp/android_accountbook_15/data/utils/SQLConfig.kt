package com.woowacamp.android_accountbook_15.data.utils

import android.provider.BaseColumns


/**
 * Setting
 */
const val SQL_FOREIGN_SET =
    "PRAGMA foreign_keys=ON"

/**
 * Create Table
 */
const val SQL_CREATE_HISTORY =
    "CREATE TABLE ${HistoryColumns.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${HistoryColumns.COLUMN_NAME_TYPE} INTEGER NOT NULL," +
            "${HistoryColumns.COLUMN_NAME_CONTENT} TEXT," +
            "${HistoryColumns.COLUMN_NAME_DATE} TEXT NOT NULL," +
            "${HistoryColumns.COLUMN_NAME_AMOUNT} INTEGER NOT NULL," +
            "${HistoryColumns.COLUMN_NAME_PAYMENT_ID} INTEGER," +
            "${HistoryColumns.COLUMN_NAME_CATEGORY_ID} INTEGER NOT NULL," +
            "FOREIGN KEY(${HistoryColumns.COLUMN_NAME_PAYMENT_ID})" +
            "REFERENCES ${PaymentMethodColumns.TABLE_NAME} (${BaseColumns._ID})," +
            "FOREIGN KEY(${HistoryColumns.COLUMN_NAME_PAYMENT_ID})" +
            "REFERENCES ${CategoryColumns.TABLE_NAME} (${BaseColumns._ID}))"

const val SQL_CREATE_PAYMENT_METHOD =
    "CREATE TABLE ${PaymentMethodColumns.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${PaymentMethodColumns.COLUMN_NAME_NAME} TEXT UNIQUE)"

const val SQL_CREATE_CATEGORY =
    "CREATE TABLE ${CategoryColumns.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${CategoryColumns.COLUMN_NAME_TYPE} INTEGER," +
            "${CategoryColumns.COLUMN_NAME_NAME} TEXT UNIQUE," +
            "${CategoryColumns.COLUMN_NAME_COLOR} INTEGER)"

/**
 * Drop Table
 */
const val SQL_DELETE_HISTORY = "DROP TABLE IF EXISTS ${HistoryColumns.TABLE_NAME}"
const val SQL_DELETE_PAYMENT_METHOD = "DROP TABLE IF EXISTS ${PaymentMethodColumns.TABLE_NAME}"
const val SQL_DELETE_CATEGORY = "DROP TABLE IF EXISTS ${CategoryColumns.TABLE_NAME}"

/**
 * Insert Row
 */
const val SQL_INSERT_PAYMENT_METHOD_1 = "INSERT INTO ${PaymentMethodColumns.TABLE_NAME} (${PaymentMethodColumns.COLUMN_NAME_NAME}) VALUES ('현대카드')"
const val SQL_INSERT_PAYMENT_METHOD_2 = "INSERT INTO ${PaymentMethodColumns.TABLE_NAME} (${PaymentMethodColumns.COLUMN_NAME_NAME}) VALUES ('카카오페이 체크카드')"
const val SQL_INSERT_EXPENSES_CATEGORY_1 = "INSERT INTO ${CategoryColumns.TABLE_NAME}" +
        " (${CategoryColumns.COLUMN_NAME_TYPE}, ${CategoryColumns.COLUMN_NAME_NAME}, ${CategoryColumns.COLUMN_NAME_COLOR})" +
        " VALUES (0, '교통', 0xFF94D3CC)"
const val SQL_INSERT_EXPENSES_CATEGORY_2 = "INSERT INTO ${CategoryColumns.TABLE_NAME}" +
        " (${CategoryColumns.COLUMN_NAME_TYPE}, ${CategoryColumns.COLUMN_NAME_NAME}, ${CategoryColumns.COLUMN_NAME_COLOR})" +
        " VALUES (0, '문화/여가', 0xFFD092E2)"
const val SQL_INSERT_EXPENSES_CATEGORY_3 = "INSERT INTO ${CategoryColumns.TABLE_NAME}" +
        " (${CategoryColumns.COLUMN_NAME_TYPE}, ${CategoryColumns.COLUMN_NAME_NAME}, ${CategoryColumns.COLUMN_NAME_COLOR})" +
        " VALUES (0, '미분류', 0xFF817DCE)"
const val SQL_INSERT_EXPENSES_CATEGORY_4 = "INSERT INTO ${CategoryColumns.TABLE_NAME}" +
        " (${CategoryColumns.COLUMN_NAME_TYPE}, ${CategoryColumns.COLUMN_NAME_NAME}, ${CategoryColumns.COLUMN_NAME_COLOR})" +
        " VALUES (0, '생활', 0xFF4A6CC3)"
const val SQL_INSERT_EXPENSES_CATEGORY_5 = "INSERT INTO ${CategoryColumns.TABLE_NAME}" +
        " (${CategoryColumns.COLUMN_NAME_TYPE}, ${CategoryColumns.COLUMN_NAME_NAME}, ${CategoryColumns.COLUMN_NAME_COLOR})" +
        " VALUES (0, '쇼핑/뷰티', 0xFF4CB8B8)"
const val SQL_INSERT_EXPENSES_CATEGORY_6 = "INSERT INTO ${CategoryColumns.TABLE_NAME}" +
        " (${CategoryColumns.COLUMN_NAME_TYPE}, ${CategoryColumns.COLUMN_NAME_NAME}, ${CategoryColumns.COLUMN_NAME_COLOR})" +
        " VALUES (0, '식비', 0xFF4CA1DE)"
const val SQL_INSERT_EXPENSES_CATEGORY_7 = "INSERT INTO ${CategoryColumns.TABLE_NAME}" +
        " (${CategoryColumns.COLUMN_NAME_TYPE}, ${CategoryColumns.COLUMN_NAME_NAME}, ${CategoryColumns.COLUMN_NAME_COLOR})" +
        " VALUES (0, '의료/건강', 0xFF6ED5EB)"
const val SQL_INSERT_INCOME_CATEGORY_1 = "INSERT INTO ${CategoryColumns.TABLE_NAME}" +
        " (${CategoryColumns.COLUMN_NAME_TYPE}, ${CategoryColumns.COLUMN_NAME_NAME}, ${CategoryColumns.COLUMN_NAME_COLOR})" +
        " VALUES (1, '월급', 0xFF9BD182)"
const val SQL_INSERT_INCOME_CATEGORY_2 = "INSERT INTO ${CategoryColumns.TABLE_NAME}" +
        " (${CategoryColumns.COLUMN_NAME_TYPE}, ${CategoryColumns.COLUMN_NAME_NAME}, ${CategoryColumns.COLUMN_NAME_COLOR})" +
        " VALUES (1, '용돈', 0xFFEDCF65)"
const val SQL_INSERT_INCOME_CATEGORY_3 = "INSERT INTO ${CategoryColumns.TABLE_NAME}" +
        " (${CategoryColumns.COLUMN_NAME_TYPE}, ${CategoryColumns.COLUMN_NAME_NAME}, ${CategoryColumns.COLUMN_NAME_COLOR})" +
        " VALUES (1, '기타', 0xFFE29C4D)"

/**
 * Select Rows
 */
const val SQL_SELECT_ALL_HISTORY =
    "SELECT " +
            "${HistoryColumns.TABLE_NAME}.${BaseColumns._ID}," +
            "${HistoryColumns.TABLE_NAME}.${HistoryColumns.COLUMN_NAME_TYPE}," +
            "${HistoryColumns.COLUMN_NAME_CONTENT}," +
            "${HistoryColumns.COLUMN_NAME_DATE}," +
            "${HistoryColumns.COLUMN_NAME_AMOUNT}," +
            "${PaymentMethodColumns.TABLE_NAME}.${BaseColumns._ID} payment_id," +
            "${PaymentMethodColumns.TABLE_NAME}.${PaymentMethodColumns.COLUMN_NAME_NAME} payment_name," +
            "${CategoryColumns.TABLE_NAME}.${BaseColumns._ID} category_id," +
            "${CategoryColumns.TABLE_NAME}.${CategoryColumns.COLUMN_NAME_TYPE} category_type," +
            "${CategoryColumns.TABLE_NAME}.${CategoryColumns.COLUMN_NAME_NAME} category_name," +
            "${CategoryColumns.COLUMN_NAME_COLOR} category_color" +
    " FROM ${HistoryColumns.TABLE_NAME}" +
    " LEFT JOIN ${PaymentMethodColumns.TABLE_NAME}" +
            " ON ${HistoryColumns.TABLE_NAME}.${HistoryColumns.COLUMN_NAME_PAYMENT_ID} = ${PaymentMethodColumns.TABLE_NAME}.${BaseColumns._ID}" +
    " LEFT JOIN ${CategoryColumns.TABLE_NAME}" +
            " ON ${HistoryColumns.TABLE_NAME}.${HistoryColumns.COLUMN_NAME_CATEGORY_ID} = ${CategoryColumns.TABLE_NAME}.${BaseColumns._ID}" +
    " WHERE substr(${HistoryColumns.COLUMN_NAME_DATE}, 1, 7) = ?" + // 2001-01-30 이면 2001-01 까지 가져온다.
    " ORDER BY ${HistoryColumns.COLUMN_NAME_DATE} ASC"