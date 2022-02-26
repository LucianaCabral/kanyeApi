package com.lcabral.kenyewestquotes.service

import com.lcabral.kenyewestquotes.model.Quote
import retrofit2.Response
import retrofit2.http.GET

interface QuoteApi {

    @GET("/")
    suspend fun getQuote(): Response<Quote>
}