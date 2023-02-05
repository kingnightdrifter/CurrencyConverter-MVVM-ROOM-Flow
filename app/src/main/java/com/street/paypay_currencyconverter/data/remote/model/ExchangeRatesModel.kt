package com.street.paypay_currencyconverter.data.remote.model

import com.street.paypay_currencyconverter.data.local.entities.CurrencyRatesEntity
import com.google.gson.annotations.SerializedName

data class ExchangeRatesModel(

    var rates: HashMap<String, Double> = HashMap(),
    @SerializedName("error")
    var status: Boolean = false,
    var message: String = ""

)


fun ExchangeRatesModel.asEntity() =  rates.map { pair ->
    CurrencyRatesEntity(
        pair.key,
        pair.value
    )
}
