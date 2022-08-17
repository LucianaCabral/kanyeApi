package com.lcabral.kenyewestquotes.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lcabral.kenyewestquotes.model.Quote
import com.lcabral.kenyewestquotes.repository.QuoteRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class QuoteViewModel(private val quoteRepository: QuoteRepository) : ViewModel() {

     val quotes = MutableLiveData<Response<Quote>>()

    fun init() {
        getQuote()
    }

    private fun getQuote() {
        viewModelScope.launch {
            val result = quoteRepository.getQuote()
            quotes.value = result
        }
    }
}