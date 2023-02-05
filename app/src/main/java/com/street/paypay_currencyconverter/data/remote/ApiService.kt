package com.street.paypay_currencyconverter.data.remote

import com.street.paypay_currencyconverter.BuildConfig
import com.street.paypay_currencyconverter.data.remote.model.CurrenciesModel
import com.street.paypay_currencyconverter.data.remote.model.ExchangeRatesModel
import com.street.paypay_currencyconverter.utils.Constants.CURRENCIES_END_POINT
import com.street.paypay_currencyconverter.utils.Constants.DEFAULT_SOURCE_CURRENCY
import com.street.paypay_currencyconverter.utils.Constants.EXCHANGE_RATES
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(EXCHANGE_RATES)
    suspend fun getExchangeRates(
        @Query("app_id") appId: String = BuildConfig.API_KEY,
        @Query("base") base: String = DEFAULT_SOURCE_CURRENCY,
    ): RemoteResponse<ExchangeRatesModel>


    @GET(CURRENCIES_END_POINT)
    suspend fun getCurrencies(

    ): RemoteResponse<CurrenciesModel>

}
