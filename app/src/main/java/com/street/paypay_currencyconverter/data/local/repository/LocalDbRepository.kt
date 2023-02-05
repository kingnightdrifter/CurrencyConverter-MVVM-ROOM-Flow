package com.street.paypay_currencyconverter.data.local.repository

import com.street.paypay_currencyconverter.data.local.dao.CurrencyDao
import com.street.paypay_currencyconverter.data.local.entities.CurrencyNamesEntity
import com.street.paypay_currencyconverter.data.local.entities.CurrencyRatesEntity
import javax.inject.Inject

class LocalDbRepository @Inject constructor(var currenciesDao: CurrencyDao) {

    fun insertCurrencyNames(currencyNameEntities: List<CurrencyNamesEntity>) {
        currenciesDao.insertCurrencyNames(currencyNameEntities)
    }

    fun getAllCurrencyNames(): List<CurrencyNamesEntity> {
        return currenciesDao.getAllCurrencyNames()
    }

    fun insertCurrencyRates(currencyRateEntities: List<CurrencyRatesEntity>) {
        currenciesDao.insertCurrencyRates(currencyRateEntities)
    }

    fun getAllCurrencyRates(): List<CurrencyRatesEntity> {
        return currenciesDao.getAllCurrencyRates()
    }
}