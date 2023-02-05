package com.street.paypay_currencyconverter.data.repository

import androidx.annotation.WorkerThread
import com.street.paypay_currencyconverter.data.local.entities.CurrencyNamesEntity
import com.street.paypay_currencyconverter.data.local.entities.CurrencyRatesEntity
import com.street.paypay_currencyconverter.data.local.repository.LocalDbRepository
import com.street.paypay_currencyconverter.data.remote.model.asEntities
import com.street.paypay_currencyconverter.data.remote.model.asEntity
import com.street.paypay_currencyconverter.data.remote.ApiService
import com.street.paypay_currencyconverter.data.remote.onErrorSuspend
import com.street.paypay_currencyconverter.data.remote.onExceptionSuspend
import com.street.paypay_currencyconverter.data.remote.onSuccessSuspend
import javax.inject.Inject

/**
 * This is an implementation of [RemoteRepository] to handle communication with [ApiService] server.
 */
class RemoteRepositoryImpl @Inject constructor(
    private val apiService: ApiService, private val localRepo: LocalDbRepository
) : RemoteRepository {

    @WorkerThread
    override suspend fun getCurrencies(): List<CurrencyNamesEntity> {

        var currencyNameList = localRepo.getAllCurrencyNames()

        if (currencyNameList.isNotEmpty()) {
            return currencyNameList
        } else {

            val response = apiService.getCurrencies()
            response.onSuccessSuspend(onResult = {
                currencyNameList = this.data!!.asEntities()
                localRepo.insertCurrencyNames(currencyNameList)
            }, isDateNull = {
                throw Exception("Unable To Fetch Data")
            }).onErrorSuspend {
                    throw Exception(this.error.message)
                }.onExceptionSuspend {
                    throw Exception(this.error.message)
                }
        }

        return currencyNameList
    }


    override suspend fun getExchangeRates(): List<CurrencyRatesEntity> {
        var currencyRateList = localRepo.getAllCurrencyRates()
        if (currencyRateList.isNotEmpty()) {

            return currencyRateList
        } else {
            apiService.getExchangeRates().apply {
                this.onSuccessSuspend(onResult = {
                    this.data?.let { exchangeRateDTO ->
                        if (exchangeRateDTO.status) {
                            throw Exception(exchangeRateDTO.message)
                        } else {
                            currencyRateList = exchangeRateDTO.asEntity()
                            localRepo.insertCurrencyRates(currencyRateList)

                        }
                    }
                }, isDateNull = {
                    throw Exception("Unable To Fetch Data")
                }).onErrorSuspend {
                    throw Exception(this.error.message)
                }.onExceptionSuspend {
                    throw Exception(this.error.message)
                }
            }

        }
        return currencyRateList
    }


}

