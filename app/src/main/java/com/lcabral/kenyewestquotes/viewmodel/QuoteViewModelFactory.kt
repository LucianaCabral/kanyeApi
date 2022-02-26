package com.lcabral.kenyewestquotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lcabral.kenyewestquotes.repository.QuoteRepository

class QuoteViewModelFactory(private val quoteRepository: QuoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return QuoteViewModel(quoteRepository) as T
    }
}