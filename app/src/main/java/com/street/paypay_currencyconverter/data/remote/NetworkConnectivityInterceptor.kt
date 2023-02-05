package com.street.paypay_currencyconverter.data.remote




import com.street.paypay_currencyconverter.App
import com.street.paypay_currencyconverter.utils.isInternetAvailable
import okhttp3.Interceptor
import okhttp3.Response
import okio.IOException

open class NetworkConnectivityInterceptor : Interceptor {

    private val isConnected: Boolean
        get() {
            return isInternetAvailable(App.getInstance())
        }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        if (!isConnected) {
            throw NoNetworkException()
        }
        return chain.proceed(originalRequest)
    }

    class NoNetworkException internal constructor() : IOException("NO INTERNET CONNECTION")
}