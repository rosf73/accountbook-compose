package com.woowacamp.android_accountbook_15.data.di

import android.content.Context
import com.woowacamp.android_accountbook_15.data.AccountBookHelper
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
}