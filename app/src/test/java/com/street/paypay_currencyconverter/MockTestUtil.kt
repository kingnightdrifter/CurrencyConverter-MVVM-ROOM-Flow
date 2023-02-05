package com.street.paypay_currencyconverter

import com.street.paypay_currencyconverter.data.remote.model.CurrenciesModel
import com.street.paypay_currencyconverter.data.remote.model.ExchangeRatesModel

class   MockTestUtil {
    companion object {
        fun getMockCurrencyDTO(): CurrenciesModel {
            val currencies: HashMap<String, String> = HashMap()
            currencies["PKR"] = "Pakistani Rupees"
            currencies["AFG"] = "Afghani"
            return CurrenciesModel().apply {
              putAll(currencies)
            }
        }

        fun getMockCurrencyRates(): ExchangeRatesModel {
            val currencies: HashMap<String, Double> = HashMap()
            currencies["PKR"] = 12.3
            currencies["AFG"] = 32.5
            return ExchangeRatesModel(currencies,status = false,"")
        }

    }
}
