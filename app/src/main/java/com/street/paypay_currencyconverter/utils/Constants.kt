package com.street.paypay_currencyconverter.utils

object Constants {

    const val FORMAT: String = "%.3f"

    //End Points
    const val EXCHANGE_RATES = "latest.json"
    const val CURRENCIES_END_POINT = "currencies.json"
    const val TIME_OUT = 1500L

    //Other constants
    const val DEFAULT_SOURCE_CURRENCY = "USD"
    const val WORKER_TAG = "FetchDataWorker"
    const val DATABASE_NAME = "paypay_currency_converter.db"
    const val TABLE_CURRENCY = "currency"
    const val TABLE_CURRENCY_RATES = "currency_rates"
}