package com.street.paypay_currencyconverter.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.street.paypay_currencyconverter.data.local.entities.CurrencyNamesEntity
import com.street.paypay_currencyconverter.data.local.entities.CurrencyRatesEntity
import com.street.paypay_currencyconverter.utils.Constants.TABLE_CURRENCY
import com.street.paypay_currencyconverter.utils.Constants.TABLE_CURRENCY_RATES

@Dao
abstract class CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertCurrencyNames(currencyNameEntities: List<CurrencyNamesEntity>)

    @Query("Select *FROM $TABLE_CURRENCY")
    abstract fun getAllCurrencyNames(): List<CurrencyNamesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertCurrencyRates(currencyRateEntities: List<CurrencyRatesEntity>)

    @Query("Select *FROM $TABLE_CURRENCY_RATES")
    abstract fun getAllCurrencyRates(): List<CurrencyRatesEntity>
}