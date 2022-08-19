package com.lcabral.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.lcabral.kenyewestquotes.model.Quote
import com.lcabral.kenyewestquotes.repository.QuoteRepository
import com.lcabral.kenyewestquotes.service.RetrofitService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class QuoteRepositoryTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()
    private val retrofitService: RetrofitService = mockk(relaxed = true)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when getQuote  is called them is Should call service return get quote`() = runBlocking {
        //Given
        val itemQuote: Response<Quote> = mockk(relaxUnitFun = true)
        coEvery { retrofitService.retrofitApi().getQuote() } returns itemQuote

        // When
        QuoteRepository(retrofitService).getQuote()

        //Then
        coVerify { retrofitService.retrofitApi().getQuote() }
    }
}