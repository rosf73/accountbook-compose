package com.woowacamp.android_accountbook_15.data.di

import android.content.Context
import com.woowacamp.android_accountbook_15.data.AccountBookDataSourceImpl
import com.woowacamp.android_accountbook_15.data.AccountBookHelper
import com.woowacamp.android_accountbook_15.data.utils.AccountBookDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AccountBookHelper
        = AccountBookHelper(context)

    @Singleton
    @Provides
    fun provideDataSource(dbHelper: AccountBookHelper): AccountBookDataSource
        = AccountBookDataSourceImpl(dbHelper)
}