package com.street.paypay_currencyconverter.di

import com.street.paypay_currencyconverter.data.local.repository.LocalDbRepository
import com.street.paypay_currencyconverter.data.remote.ApiService
import com.street.paypay_currencyconverter.data.repository.RemoteRepository
import com.street.paypay_currencyconverter.data.repository.RemoteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * The Dagger Module for providing repository instances.
 */
@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {


    @Singleton
    @Provides
    fun provideRepository(apiService: ApiService, localDbRepository: LocalDbRepository): RemoteRepository {
        return RemoteRepositoryImpl( apiService, localDbRepository)
    }
}
