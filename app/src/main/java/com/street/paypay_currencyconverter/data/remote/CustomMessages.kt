package com.street.paypay_currencyconverter.data.remote

sealed class CustomMessages(val message: String = "") {

    object Timeout : CustomMessages()
    object EmptyData : CustomMessages()
    object ServerBusy : CustomMessages()
    object HttpException : CustomMessages()
    object SocketTimeOutException : CustomMessages()
    object NoInternet : CustomMessages()
    object Unauthorized : CustomMessages()
    object InternalServerError : CustomMessages()
    object BadRequest : CustomMessages()
    object Conflict : CustomMessages()
    object NotFound : CustomMessages()
    object NotAcceptable : CustomMessages()
    object ServiceUnavailable : CustomMessages()
    object Forbidden : CustomMessages()
    data class SomethingWentWrong(val error: String) : CustomMessages(message = error)


}