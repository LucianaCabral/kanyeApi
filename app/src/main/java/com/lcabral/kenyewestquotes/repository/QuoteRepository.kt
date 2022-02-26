package com.lcabral.kenyewestquotes.repository

import com.lcabral.kenyewestquotes.model.Quote
import com.lcabral.kenyewestquotes.service.RetrofitService
import retrofit2.Response

class QuoteRepository(private val retrofitService: RetrofitService) {

    suspend fun getQuote(): Response<Quote> {
        return retrofitService.retrofitApi().getQuote()
    }
}