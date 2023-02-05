package com.street.paypay_currencyconverter

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.street.paypay_currencyconverter.utils.Constants.WORKER_TAG
import com.street.paypay_currencyconverter.worker.PriodicThirtyMinWorker
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), Configuration.Provider {



    init {
        instance = this
    }


    companion object {

        private var instance: App? = null
        fun getInstance(): App {
            synchronized(App::class.java) {
                if (instance == null)
                 instance =
                   App()

            }
            return instance!!
        }




    }
    @Inject
    lateinit var workerFactory: HiltWorkerFactory
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        initWorkManager()
    }

    override fun getWorkManagerConfiguration() = Configuration.Builder()
        .setWorkerFactory(workerFactory)
        .setMinimumLoggingLevel(android.util.Log.DEBUG)
        .build()

    private fun initWorkManager() {
        scope.launch {
            val worker = PeriodicWorkRequestBuilder<PriodicThirtyMinWorker>(
                30,
                TimeUnit.MINUTES
            ).addTag(WORKER_TAG).setInitialDelay(30, TimeUnit.MINUTES).build()

            WorkManager.getInstance(applicationContext)
                .enqueueUniquePeriodicWork(
                    WORKER_TAG,
                    ExistingPeriodicWorkPolicy.KEEP,
                    worker
                )
        }
    }

}
