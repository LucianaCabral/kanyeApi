package com.lcabral.kenyewestquotes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lcabral.kenyewestquotes.model.Quote
import com.lcabral.kenyewestquotes.repository.QuoteRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class QuoteViewModel(private val quoteRepository: QuoteRepository) : ViewModel() {

    private val _quotes : MutableLiveData<Response<Quote>> = MutableLiveData()
    val quotes: LiveData<Response<Quote>>
    get() = _quotes

    fun getQuote() {
        viewModelScope.launch {
            _quotes.value = quoteRepository.getQuote()
        }
    }
}