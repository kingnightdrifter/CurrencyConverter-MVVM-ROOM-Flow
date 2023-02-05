package com.street.paypay_currencyconverter.presentation

import com.street.paypay_currencyconverter.data.remote.CustomMessages


sealed class UIState{
    object LoadingState : UIState()
    object ContentState : UIState()
    object EmptyState : UIState()
    class ErrorState(val message: CustomMessages) : UIState()
}

