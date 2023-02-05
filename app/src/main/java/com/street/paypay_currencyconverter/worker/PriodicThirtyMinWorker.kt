package com.street.paypay_currencyconverter.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.street.paypay_currencyconverter.data.local.entities.CurrencyNamesEntity
import com.street.paypay_currencyconverter.data.local.entities.CurrencyRatesEntity
import com.street.paypay_currencyconverter.data.local.repository.LocalDbRepository
import com.street.paypay_currencyconverter.data.repository.RemoteRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class PriodicThirtyMinWorker @AssistedInject constructor(
    private val localDbRepository: LocalDbRepository,
    private val remoteRepository: RemoteRepository,
    @Assisted val context: Context,
    @Assisted workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {

        var result = Result.failure()
        withContext(Dispatchers.IO) {

            result = try {
                val currencies = remoteRepository.getCurrencies()
                val exchangeRates = remoteRepository.getExchangeRates()

                result = if (currencies.isNotEmpty()) {
                    saveCurrencyNamesIntoDatabase(currencies)
                    Result.success()
                } else {
                    Result.failure()
                }
                result = if (exchangeRates.isNotEmpty()) {
                    saveCurrencyRatesIntoDatabase(exchangeRates)
                    Result.success()
                } else {
                    Result.failure()
                }
                result
            } catch (e: java.lang.Exception) {
                Result.failure()
            }

        }




        return result
    }

    private fun saveCurrencyNamesIntoDatabase(currencyNameListEntity: List<CurrencyNamesEntity>) {
        localDbRepository.insertCurrencyNames(currencyNameListEntity)
    }

    private fun saveCurrencyRatesIntoDatabase(currencyRatesEntityList: List<CurrencyRatesEntity>) {
        localDbRepository.insertCurrencyRates(currencyRatesEntityList)
    }
}