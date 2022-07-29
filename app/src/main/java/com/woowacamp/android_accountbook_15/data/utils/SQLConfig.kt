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
            "${HistoryColumns.COLUMN_NAME_TYPE} INTEGER," +
            "${HistoryColumns.COLUMN_NAME_CONTENT} TEXT," +
            "${HistoryColumns.COLUMN_NAME_DATE} DATETIME," +
            "${HistoryColumns.COLUMN_NAME_AMOUNT} INTEGER," +
            "${HistoryColumns.COLUMN_NAME_PAYMENT_ID} INTEGER," +
            "${HistoryColumns.COLUMN_NAME_CATEGORY_ID} INTEGER," +
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
            "${CategoryColumns.COLUMN_NAME_COLOR} INTEGER UNIQUE)"

/**
 * Drop Table
 */
const val SQL_DELETE_HISTORY = "DROP TABLE IF EXISTS ${HistoryColumns.TABLE_NAME}"
const val SQL_DELETE_PAYMENT_METHOD = "DROP TABLE IF EXISTS ${PaymentMethodColumns.TABLE_NAME}"
const val SQL_DELETE_CATEGORY = "DROP TABLE IF EXISTS ${CategoryColumns.TABLE_NAME}"

/**
 * Select Rows
 */
const val SQL_SELECT_ALL_HISTORY =
    "SELECT " +
            "${HistoryColumns.TABLE_NAME}.${BaseColumns._ID}," +
            "${HistoryColumns.TABLE_NAME}.${HistoryColumns.COLUMN_NAME_TYPE}," +
            "${HistoryColumns.TABLE_NAME}.${HistoryColumns.COLUMN_NAME_CONTENT}," +
            "${HistoryColumns.TABLE_NAME}.${HistoryColumns.COLUMN_NAME_DATE}," +
            "${HistoryColumns.TABLE_NAME}.${HistoryColumns.COLUMN_NAME_AMOUNT}," +
            "${PaymentMethodColumns.TABLE_NAME}.${BaseColumns._ID} AS payment_id," +
            "${PaymentMethodColumns.TABLE_NAME}.${PaymentMethodColumns.COLUMN_NAME_NAME} AS payment_name," +
            "${CategoryColumns.TABLE_NAME}.${BaseColumns._ID} AS category_id," +
            "${CategoryColumns.TABLE_NAME}.${CategoryColumns.COLUMN_NAME_TYPE} AS category_type," +
            "${CategoryColumns.TABLE_NAME}.${CategoryColumns.COLUMN_NAME_NAME} AS category_name," +
            "${CategoryColumns.TABLE_NAME}.${CategoryColumns.COLUMN_NAME_COLOR} AS category_color" +
    " FROM ${HistoryColumns.TABLE_NAME}" +
    " LEFT JOIN ${PaymentMethodColumns.TABLE_NAME}" +
            " ON ${HistoryColumns.TABLE_NAME}.${BaseColumns._ID} = ${PaymentMethodColumns.TABLE_NAME}.${BaseColumns._ID}" +
    " LEFT JOIN ${CategoryColumns.TABLE_NAME}" +
            " ON ${HistoryColumns.TABLE_NAME}.${BaseColumns._ID} = ${CategoryColumns.TABLE_NAME}.${BaseColumns._ID}"