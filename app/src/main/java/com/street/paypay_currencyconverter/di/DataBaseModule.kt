package com.street.paypay_currencyconverter.di

import android.content.Context
import com.street.paypay_currencyconverter.data.local.LocalRoomDatabase
import com.street.paypay_currencyconverter.data.local.dao.CurrencyDao
import com.street.paypay_currencyconverter.data.local.repository.LocalDbRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Singleton
    @Provides
    fun providesLocalRepository(currenciesDao: CurrencyDao): LocalDbRepository {
        return LocalDbRepository(currenciesDao)
    }

    @Singleton
    @Provides
    fun provideCurrenciesDao(localRoomDatabase: LocalRoomDatabase): CurrencyDao {
        return localRoomDatabase.currenciesDao()
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): LocalRoomDatabase {
        return LocalRoomDatabase.getInstance(context)
    }

}