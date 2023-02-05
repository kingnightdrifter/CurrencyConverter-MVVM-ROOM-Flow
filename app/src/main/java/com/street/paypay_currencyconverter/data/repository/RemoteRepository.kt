package com.street.paypay_currencyconverter.data.repository

import com.street.paypay_currencyconverter.data.local.entities.CurrencyNamesEntity
import com.street.paypay_currencyconverter.data.local.entities.CurrencyRatesEntity

/**
 * Repository is an interface data layer to handle communication with any data source such as Server or local database.
 * @see [RemoteRepositoryImpl] for implementation of this class to utilize APIService.
 */
interface RemoteRepository {

    suspend fun getCurrencies(): List<CurrencyNamesEntity>
    suspend fun getExchangeRates(): List<CurrencyRatesEntity>
}
