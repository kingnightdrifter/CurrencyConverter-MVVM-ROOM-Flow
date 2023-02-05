package com.street.paypay_currencyconverter.data.remote


import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException


enum class ErrorCodes(val code: Int) {
    SocketTimeOut(-1),
    BadRequest(400),
    NotFound(404),
    Conflict(409),
    InternalServerError(500),
    Forbidden(403),
    NotAcceptable(406),
    ServiceUnavailable(503),
    UnAuthorized(401),
}



fun <T : Any> handleException(throwable: Exception): CustomMessages {


    return when (throwable) {


        is HttpException -> CustomMessages.HttpException
        is TimeoutException -> CustomMessages.Timeout
        is NetworkConnectivityInterceptor.NoNetworkException -> CustomMessages.NoInternet
        is UnknownHostException -> CustomMessages.ServerBusy
        is ConnectException -> CustomMessages.NoInternet
        is SocketTimeoutException -> CustomMessages.SocketTimeOutException
        else -> CustomMessages.NoInternet
    }
}


fun <T : Any> handleException(statusCode: Int, message: String): CustomMessages {
    return getErrorType(statusCode, message)
}

private fun getErrorType(code: Int, message: String): CustomMessages {
    return when (code) {
        ErrorCodes.SocketTimeOut.code -> CustomMessages.Timeout
        ErrorCodes.UnAuthorized.code -> CustomMessages.Unauthorized
        ErrorCodes.InternalServerError.code -> CustomMessages.InternalServerError

        ErrorCodes.BadRequest.code -> CustomMessages.BadRequest
        ErrorCodes.Conflict.code -> CustomMessages.Conflict
        ErrorCodes.InternalServerError.code -> CustomMessages.InternalServerError

        ErrorCodes.NotFound.code -> CustomMessages.NotFound
        ErrorCodes.NotAcceptable.code -> CustomMessages.NotAcceptable
        ErrorCodes.ServiceUnavailable.code -> CustomMessages.ServiceUnavailable
        ErrorCodes.Forbidden.code -> CustomMessages.Forbidden
        else -> CustomMessages.SomethingWentWrong(message)
    }
}
