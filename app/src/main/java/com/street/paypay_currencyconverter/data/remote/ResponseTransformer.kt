package com.street.paypay_currencyconverter.data.remote


/**
 * A suspend function for handling success response.
 */
@SuspensionFunction
suspend fun <T> RemoteResponse<T>.onSuccessSuspend(
    onResult: suspend RemoteResponse.RemoteSuccessResponse<T>.() -> Unit,
    isDateNull: () -> Nothing
): RemoteResponse<T> {
    if (this is RemoteResponse.RemoteSuccessResponse) {
        if (this.data == null) {
            isDateNull()
        }else{
            onResult(this)
        }

    }
    return this
}

/**
 * A suspend function for handling error response.
 */
@SuspensionFunction
suspend fun <T> RemoteResponse<T>.onErrorSuspend(
    onResult: suspend RemoteResponse.ApiFailureResponse.Error<T>.() -> Unit
): RemoteResponse<T> {
    if (this is RemoteResponse.ApiFailureResponse.Error) {
        onResult(this)
    }
    return this
}

/**
 * A suspend function for handling exception response.
 */
@SuspensionFunction
suspend fun <T> RemoteResponse<T>.onExceptionSuspend(
    onResult: suspend RemoteResponse.ApiFailureResponse.Exception<T>.() -> Unit
): RemoteResponse<T> {
    if (this is RemoteResponse.ApiFailureResponse.Exception) {
        onResult(this)
    }
    return this
}

/** A message from the [RemoteResponse.ApiFailureResponse.Error]. */
fun <T> RemoteResponse.ApiFailureResponse.Error<T>.message(): String = toString()

/** A message from the [RemoteResponse.ApiFailureResponse.Exception]. */
fun <T> RemoteResponse.ApiFailureResponse.Exception<T>.message(): String = toString()