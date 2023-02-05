package com.street.paypay_currencyconverter.data.remote

import retrofit2.Response

/**
 * Common class used by API responses.
 */
sealed class RemoteResponse<out T> {

    /**
     * API Success response class from retrofit.
     *
     * [data] is optional. (There are responses without data)
     */
    data class RemoteSuccessResponse<T>(val response: Response<T>) : RemoteResponse<T>() {
        val data: T? = response.body()
    }

    /**
     * Failure class represents two types of Failure:
     * 1) ### Error response e.g. server error
     * 2) ### Exception response e.g. network connection error
     */
    sealed class ApiFailureResponse<T> {
        data class Error<T>(val response: Response<T>) : RemoteResponse<T>(){
            val error: CustomMessages = handleException<Int>(response.code(), response.message())
        }

        data class Exception<T>(val exception: Throwable) : RemoteResponse<T>() {
            val error: CustomMessages = handleException<java.lang.Exception>(exception as kotlin.Exception)
        }
    }

    companion object {

        /**
         * ApiResponse error Factory.
         *
         * [ApiFailureResponse] factory function. Only receives [Throwable] as an argument.
         */
        fun <T> exception(ex: Throwable) = ApiFailureResponse.Exception<T>(ex)

        /**
         * ApiResponse error Factory.
         *
         * [ApiFailureResponse] factory function.
         */
        fun <T> error(response: Response<T>) = ApiFailureResponse.Error<T>(response)

        /**
         * ApiResponse Factory.
         *
         * [create] Create [RemoteResponse] from [retrofit2.Response] returning from the block.
         * If [retrofit2.Response] has no errors, it creates [RemoteResponse.RemoteSuccessResponse].
         * If [retrofit2.Response] has errors, it creates [RemoteResponse.ApiFailureResponse.Error].
         * If [retrofit2.Response] has occurred exceptions, it creates [RemoteResponse.ApiFailureResponse.Exception].
         */
        fun <T> create(
            successCodeRange: IntRange = 200..299,
            response: Response<T>
        ): RemoteResponse<T> = try {
            if (response.raw().code in successCodeRange) {
                RemoteSuccessResponse(response)
            } else {
                ApiFailureResponse.Error(response)
            }
        } catch (ex: Exception) {
            ApiFailureResponse.Exception(ex)
        }
    }
}
