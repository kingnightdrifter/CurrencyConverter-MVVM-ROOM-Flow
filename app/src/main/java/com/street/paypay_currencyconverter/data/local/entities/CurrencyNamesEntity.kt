package com.street.paypay_currencyconverter.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.street.paypay_currencyconverter.domain.domain_models.CurrencyNameDomainModel
import com.street.paypay_currencyconverter.utils.Constants.TABLE_CURRENCY

@Entity(tableName = TABLE_CURRENCY)
data class CurrencyNamesEntity(
    @PrimaryKey
    val currencyName: String,
    var currencyCountryName: String
)

 fun List<CurrencyNamesEntity>.asDomainModel()= map {

     CurrencyNameDomainModel(
         currencyCountryName = it.currencyCountryName,
         currencyName = it.currencyName
     )
 }


