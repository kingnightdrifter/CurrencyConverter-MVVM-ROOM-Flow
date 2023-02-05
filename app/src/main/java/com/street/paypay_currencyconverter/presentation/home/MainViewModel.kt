package com.street.paypay_currencyconverter.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.street.paypay_currencyconverter.data.remote.CustomMessages
import com.street.paypay_currencyconverter.domain.domain_models.CurrencyNameDomainModel
import com.street.paypay_currencyconverter.domain.domain_models.ExchangeRatesDomainModel
import com.street.paypay_currencyconverter.domain.usecase.GetCurrencies
import com.street.paypay_currencyconverter.domain.usecase.GetExchangeRates
import com.street.paypay_currencyconverter.presentation.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCurrenciesUseCAse: GetCurrencies,
    private val getExchangeRates: GetExchangeRates
) : ViewModel() {

    private var _uiState = MutableLiveData<UIState>()
    var uiStateLiveData: LiveData<UIState> = _uiState

    private var _currenciesList = MutableLiveData<List<CurrencyNameDomainModel>>()
    var currenciesLiveData: LiveData<List<CurrencyNameDomainModel>> = _currenciesList

    private var _exchangeRateUiState = MutableLiveData<UIState>()
    var exchangeRateUiStateLiveData: LiveData<UIState> = _exchangeRateUiState
    private var _exchangeRatesList = MutableLiveData<List<ExchangeRatesDomainModel>>()
    var exchangeRatesLiveData: LiveData<List<ExchangeRatesDomainModel>> = _exchangeRatesList

    init {
        fetchCurrencies()
    }


    private fun fetchCurrencies() {
        _uiState.postValue(UIState.LoadingState)
        viewModelScope.launch(Dispatchers.IO) {
            getCurrenciesUseCAse().catch { exception ->
                _uiState.postValue(
                    UIState.ErrorState(
                        CustomMessages.SomethingWentWrong(
                            exception.message ?: ""
                        )
                    )
                )

            }.collect { data ->
                if (data.isEmpty()) {
                    _uiState.postValue(
                        UIState.ErrorState(
                            CustomMessages.EmptyData
                        )
                    )
                } else {
                    _uiState.postValue(
                        UIState.ContentState
                    )
                    _currenciesList.postValue(data)
                }
            }
        }
    }


    fun fetchExchangeRates(source: String, amount: Double) {

        viewModelScope.launch(Dispatchers.IO) {
            getExchangeRates(source = source, amount = amount).catch { exception ->
                _exchangeRateUiState.postValue(
                    UIState.ErrorState(
                        CustomMessages.SomethingWentWrong(
                            exception.message ?: ""
                        )
                    )
                )
            }.collect { data ->
                _exchangeRateUiState.postValue(
                    UIState.ContentState)
                _exchangeRatesList.postValue(data)
            }
        }
    }
}
