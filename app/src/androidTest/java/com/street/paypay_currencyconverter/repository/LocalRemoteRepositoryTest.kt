package com.street.paypay_currencyconverter.repository

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.street.paypay_currencyconverter.data.local.LocalRoomDatabase
import com.street.paypay_currencyconverter.data.local.dao.CurrencyDao
import com.street.paypay_currencyconverter.data.local.entities.CurrencyNamesEntity
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class LocalRemoteRepositoryTest {

    private lateinit var currenciesDao: CurrencyDao
    private lateinit var localRoomDatabase: LocalRoomDatabase

    @Before
    fun start() {
        localRoomDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LocalRoomDatabase::class.java
        ).allowMainThreadQueries().build()
        currenciesDao = localRoomDatabase.currenciesDao()
    }

    @Test
    fun insertCurrencyNamesItem() = runBlocking {
        val item = CurrencyNamesEntity(
            currencyName = "USD",
            currencyCountryName = "United States",
        )
        currenciesDao.insertCurrencyNames(listOf(item))
        val result = currenciesDao.getAllCurrencyNames()
        assertNotNull(result)
    }
}