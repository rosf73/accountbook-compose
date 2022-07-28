package com.woowacamp.android_accountbook_15.data.repository

import android.content.Context
import com.woowacamp.android_accountbook_15.data.dao.CategoryDAO
import com.woowacamp.android_accountbook_15.data.model.Category
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class CategoryRepository @Inject constructor (
    @ActivityContext context: Context
) {

    private val dao = CategoryDAO(context)

    suspend fun createCategory(type: Int, name: String, color: String): Boolean {
        dao.insertCategory(type, name, color)
        return true
    }

    suspend fun readAllCategories(): List<Category> = dao.readAllCategories()
}