package com.street.paypay_currencyconverter.data.remote

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

/**
 * A Retrofit adapter that converts the Call into a ApiResponse
 */
class ResponseCallAdapter constructor(
    private val responseType: Type
) : CallAdapter<Type, Call<RemoteResponse<Type>>> {

    override fun responseType(): Type {
        return responseType
    }

    override fun adapt(call: Call<Type>): Call<RemoteResponse<Type>> {
        return ApiResponseCall(call)
    }

    internal class ApiResponseCall(private val call: Call<Type>) : Call<RemoteResponse<Type>> {
        override fun enqueue(callback: Callback<RemoteResponse<Type>>) {
            call.enqueue(object : Callback<Type> {
                override fun onResponse(call: Call<Type>, response: Response<Type>) {
                    val remoteResponse = RemoteResponse.create(response = response)
                    callback.onResponse(this@ApiResponseCall, Response.success(remoteResponse))
                }

                override fun onFailure(call: Call<Type>, t: Throwable) {
                    callback.onResponse(
                        this@ApiResponseCall, Response.success(RemoteResponse.exception(t))
                    )
                }
            })
        }

        override fun cancel() = call.cancel()
        override fun request(): Request = call.request()
        override fun isExecuted() = call.isExecuted
        override fun isCanceled() = call.isCanceled
        override fun execute(): Response<RemoteResponse<Type>> =
            throw UnsupportedOperationException("ApiResponseCallAdapter doesn't support execute")

        override fun timeout(): Timeout = Timeout.NONE
        override fun clone(): Call<RemoteResponse<Type>> = this
    }
}
