package com.street.paypay_currencyconverter.domain.usecase

import com.street.paypay_currencyconverter.data.local.entities.asDomainModel
import com.street.paypay_currencyconverter.data.repository.RemoteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetExchangeRates @Inject constructor(
    private val remoteRepository: RemoteRepository,

    ) {

    @ExperimentalCoroutinesApi
    suspend operator fun invoke(
        source: String,
        amount: Double
    ) = flow {

        val ratesList = remoteRepository.getExchangeRates()

        val sourceRate = ratesList.find { it.currencyName == source }?.currencyExchangeValue ?: 0.0

        val convertedList = ratesList.asDomainModel(sourceRate, amount)


        emit(convertedList)

    }


}
