package com.street.paypay_currencyconverter.usecase

import com.street.paypay_currencyconverter.MockTestUtil
import com.street.paypay_currencyconverter.data.remote.model.asEntity
import com.street.paypay_currencyconverter.data.repository.RemoteRepository
import com.street.paypay_currencyconverter.domain.usecase.GetExchangeRates
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GetExchangeRatesTest {

    @MockK
    private lateinit var remoteRepository: RemoteRepository


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `test invoking FetchCurrencyRatesUseCase gives CurrenciesDTO`() = runBlocking {
        // Given
        val fetchExchangeRatesUsecase = GetExchangeRates(remoteRepository)
        val givenExchangeRatesDTO = MockTestUtil.getMockCurrencyRates()

        // When
        coEvery { remoteRepository.getExchangeRates() }
            .returns(givenExchangeRatesDTO.asEntity())

        // Invoke
        val exchangeRatesDTO = fetchExchangeRatesUsecase("PKR",12.2)

        // Then
        MatcherAssert.assertThat(exchangeRatesDTO, CoreMatchers.notNullValue())
    }
}