package com.street.paypay_currencyconverter.presentation.home

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.street.paypay_currencyconverter.data.remote.CustomMessages
import com.street.paypay_currencyconverter.domain.domain_models.CurrencyNameDomainModel
import com.street.paypay_currencyconverter.domain.domain_models.ExchangeRatesDomainModel
import com.street.paypay_currencyconverter.utils.Constants
import com.street.paypay_currencyconverter.utils.gone
import com.street.paypay_currencyconverter.utils.visible
import com.street.paypay_currencyconverter.presentation.UIState
import com.street.paypay_currencyconverter.presentation.adapters.ExchangeRatesAdapter
import com.google.android.material.snackbar.Snackbar
import com.street.paypay_currencyconverter.R
import com.street.paypay_currencyconverter.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private var currencyEntities: MutableList<CurrencyNameDomainModel> =
        ArrayList()
    private var selectedCurrency: String = Constants.DEFAULT_SOURCE_CURRENCY
    private lateinit var exchangeRatesAdapter: ExchangeRatesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
        observeState()
        initObservations()
    }

    private fun showSnackbar(message: CustomMessages, binding: View) {

        val error = when (message) {
            is CustomMessages.EmptyData -> {
                getString(R.string.no_data_found)
            }
            is CustomMessages.Timeout -> {
                getString(R.string.timeout)
            }
            is CustomMessages.ServerBusy -> {
                getString(R.string.server_is_busy)
            }

            is CustomMessages.HttpException -> {
                getString(R.string.no_internet_connection)
            }
            is CustomMessages.SocketTimeOutException -> {
                getString(R.string.no_internet_connection)
            }
            is CustomMessages.NoInternet -> {
                getString(R.string.no_internet_connection)
            }
            is CustomMessages.Unauthorized -> {
                getString(R.string.unauthorized)
            }
            is CustomMessages.InternalServerError -> {
                getString(R.string.internal_server_error)
            }
            is CustomMessages.BadRequest -> {
                getString(R.string.bad_request)
            }
            is CustomMessages.Conflict -> {
                getString(R.string.confirm)
            }
            is CustomMessages.NotFound -> {
                getString(R.string.not_found)
            }
            is CustomMessages.NotAcceptable -> {
                getString(R.string.not_acceptable)
            }
            is CustomMessages.ServiceUnavailable -> {
                getString(R.string.service_unavailable)
            }
            is CustomMessages.Forbidden -> {
                getString(R.string.forbidden)
            }

            else -> {
                "Something went Wrong."
            }
        }

        Snackbar.make(binding.rootView, error, Snackbar.LENGTH_LONG)
            .setActionTextColor(ContextCompat.getColor(this, R.color.white)).also {
                it.setAction(
                    "OK"
                ) { v ->

                    it.dismiss()
                }
            }
            .show()


    }

    private fun observeState() {
        viewModel.uiStateLiveData.observe(this) { uiState ->
            when (uiState) {
                is UIState.LoadingState -> {
                    binding.ivConversionIcon.visible()
                    binding.progressBarCurrencies.visible()
                }
                is UIState.ContentState -> {
                    binding.ivConversionIcon.gone()
                    binding.progressBarCurrencies.gone()
                }
                is UIState.ErrorState -> {
                    showSnackbar(uiState.message, binding.rootView)
                }
                else -> {
                    binding.ivConversionIcon.gone()
                    binding.progressBarCurrencies.gone()
                }
            }

           viewModel.exchangeRateUiStateLiveData.observe(this){ state ->
               when (state) {
                is UIState.LoadingState -> {
                    binding.ivConversionIcon.visible()
                    binding.progressBarCurrencies.visible()
                }
                is UIState.ContentState -> {
                    binding.ivConversionIcon.gone()
                    binding.progressBarCurrencies.gone()
                }
                is UIState.ErrorState -> {
                    showSnackbar(state.message, binding.rootView)
                }
                else -> {
                    binding.ivConversionIcon.gone()
                    binding.progressBarCurrencies.gone()
                }
            }
           }

        }
    }

    private fun initListener() {

        binding.currenciesSpinner.onItemSelectedListener = this
        exchangeRatesAdapter = ExchangeRatesAdapter()
        binding.rvConvertedCurrencies.adapter = exchangeRatesAdapter
        binding.btnConvert.setOnClickListener {
            if (checkValidation()) {
                hideKeyboard()
                viewModel.fetchExchangeRates(
                    selectedCurrency,
                    binding.etAmount.text.toString().toDouble()
                )
            }
        }
    }

    private fun initObservations() {


        viewModel.currenciesLiveData.observe(this) { response ->
            // Update the UI, in this case
            response?.let {
                currencyEntities = response.toMutableList()
                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item,
                    response.map { it.currencyCountryName }.sorted()
                )
                binding.currenciesSpinner.adapter = adapter
            }
        }


        val exchangeRatesObserver = Observer<List<ExchangeRatesDomainModel>> { response ->
            // Update the UI, in this case
            response?.let {
                if (response.isNotEmpty()) {
                    exchangeRatesAdapter.differ.submitList(response)
                }
            }
        }
        viewModel.exchangeRatesLiveData.observe(this, exchangeRatesObserver)
    }


    private fun checkValidation(): Boolean {
        binding.etAmount.error = null
        if (binding.etAmount.text.isNullOrEmpty() ||
            binding.etAmount.text.isNullOrBlank() ||
            binding.etAmount.text?.trim().toString() == "."
        ) {
            binding.etAmount.error = getString(R.string.enter_amount_error)
            return false
        }
        return true
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        binding.currenciesSpinner.setSelection(pos)
        selectedCurrency =  currencyEntities.single() {
            it.currencyCountryName == binding.currenciesSpinner.selectedItem.toString()
        }.currencyName
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}