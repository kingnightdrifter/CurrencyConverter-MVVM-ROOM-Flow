package com.street.paypay_currencyconverter.usecase

import com.street.paypay_currencyconverter.MockTestUtil
import com.street.paypay_currencyconverter.data.local.entities.asDomainModel
import com.street.paypay_currencyconverter.data.remote.model.asDomainModel
import com.street.paypay_currencyconverter.data.repository.RemoteRepository
import com.street.paypay_currencyconverter.domain.usecase.GetCurrencies
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
class FetchCurrenciesUseCaseTest {

    @MockK
    private lateinit var remoteRepository: RemoteRepository



    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `test invoking FetchCurrencyNamesUseCase gives CurrenciesDTO`() = runBlocking {
        // Given
        val fetchCurrencyNamesUseCase = GetCurrencies(remoteRepository)
        val givenCurrencyDTO = MockTestUtil.getMockCurrencyDTO()

        // When
        coEvery { remoteRepository.getCurrencies().asDomainModel() }
            .returns(givenCurrencyDTO.asDomainModel())

        // Invoke
        val currencyDTO = fetchCurrencyNamesUseCase()

        // Then
        MatcherAssert.assertThat(currencyDTO, CoreMatchers.notNullValue())
    }
}