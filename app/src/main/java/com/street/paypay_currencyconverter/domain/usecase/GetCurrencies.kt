package com.street.paypay_currencyconverter.domain.usecase

import com.street.paypay_currencyconverter.data.local.entities.asDomainModel
import com.street.paypay_currencyconverter.data.repository.RemoteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCurrencies @Inject constructor(
    private val remoteRepository: RemoteRepository
) {

    @ExperimentalCoroutinesApi
    suspend operator fun invoke() = flow {

        val currenciesList = remoteRepository.getCurrencies().asDomainModel()
        emit(currenciesList)
    }



}
