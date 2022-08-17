package com.lcabral.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.lcabral.kenyewestquotes.model.Quote
import com.lcabral.kenyewestquotes.repository.QuoteRepository
import com.lcabral.kenyewestquotes.viewmodel.QuoteViewModel
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class QuoteViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var viewModel: QuoteViewModel
    private val repository = mockk<QuoteRepository>()
    private val quoteObserver: Observer<Response<Quote>> = mockk(relaxed = true)
    private val mockedList: Response<Quote> = mockk(relaxed = true)


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }
//    val factory = QuoteViewModelFactory()


    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun instantiateViewModel(): QuoteViewModel {
        return QuoteViewModel(repository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `init Should init quotes `() {

        //When
        instantiateViewModel().init()

        // Then
        coVerify { repository.getQuote() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
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

    private fun instantiate(): QuoteViewModel {
        viewModel = QuoteViewModel(repository)
        return viewModel
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `quotes Should return observers`() {
        // Given
        clearMocks(quoteObserver)
        val viewModel = instantiate().quotes

        coEvery { repository.getQuote() } coAnswers {
            delay(1000)
            mockedList
        }

        coEvery { viewModel.observeForever(quoteObserver) }

        //When
        instantiate().init()

        //Then
        testDispatcher.scheduler.advanceTimeBy(1000)
        coVerify { repository.getQuote() }
        coVerify { quoteObserver.onChanged(mockedList) }
    }
}





