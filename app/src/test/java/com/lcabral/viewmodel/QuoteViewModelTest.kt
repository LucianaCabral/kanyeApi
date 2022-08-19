package com.lcabral.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.lcabral.kenyewestquotes.model.Quote
import com.lcabral.kenyewestquotes.repository.QuoteRepository
import com.lcabral.kenyewestquotes.viewmodel.QuoteViewModel
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class QuoteViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var viewModel: QuoteViewModel
    private val repository = mockk<QuoteRepository>()
    private val quoteObserver: Observer<Response<Quote>> = mockk(relaxed = true)
    private val mockedList: Response<Quote> = mockk(relaxed = true)


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        instantiateViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun instantiateViewModel(): QuoteViewModel {
        viewModel = QuoteViewModel(repository)
        quoteObserver.onChanged(mockedList)
        return viewModel
    }

    @Test
    fun `init Should init quotes `() {

        //When
       instantiateViewModel().init()

        // Then
        coVerify { repository.getQuote() }
    }

    @Test
    fun `getQuote Should get quotes When call the repository`() {
        // Given
        clearMocks(quoteObserver)
        coEvery { repository.getQuote() } returns mockedList

        //When
        instantiateViewModel().init()

        // Then
        coVerify { repository.getQuote() }
    }

    @Test
    fun `quotes Should return observers`() {
        // Given
        clearMocks(quoteObserver)

        coEvery { repository.getQuote() } coAnswers {
            delay(1000)
            mockedList
        }

        //When
       instantiateViewModel()

        //Then
        testDispatcher.scheduler.advanceTimeBy(1000)
        coVerify { quoteObserver.onChanged(mockedList) }
    }
}






