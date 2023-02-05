package com.street.paypay_currencyconverter.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.street.paypay_currencyconverter.domain.domain_models.ExchangeRatesDomainModel
import com.street.paypay_currencyconverter.utils.Constants
import com.street.paypay_currencyconverter.utils.Constants.TABLE_CURRENCY_RATES

@Entity(tableName = TABLE_CURRENCY_RATES)
data class CurrencyRatesEntity(
    @PrimaryKey
    var currencyName: String,
    var currencyExchangeValue: Double
)

fun CurrencyRatesEntity.asDomainModel(sourceRate: Double, amount: Double) =
    ExchangeRatesDomainModel(
        currencyName,

        String.format(Constants.FORMAT, currencyExchangeValue * amount / sourceRate),

        )

fun List<CurrencyRatesEntity>.asDomainModel(sourceRate: Double, amount: Double) =
    map { it.asDomainModel(sourceRate, amount) }