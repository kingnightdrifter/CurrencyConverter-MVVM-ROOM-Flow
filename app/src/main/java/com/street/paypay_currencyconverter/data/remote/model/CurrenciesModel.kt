package com.street.paypay_currencyconverter.data.remote.model

import com.street.paypay_currencyconverter.data.local.entities.CurrencyNamesEntity
import com.street.paypay_currencyconverter.data.local.entities.asDomainModel

class CurrenciesModel: HashMap<String, String>()

fun CurrenciesModel.asEntities()=getModelFromResponse()

fun CurrenciesModel.asDomainModel() = getModelFromResponse().asDomainModel()
private fun CurrenciesModel.getModelFromResponse() = map { pair ->
    CurrencyNamesEntity(
        pair.key,
        pair.value
    )
}