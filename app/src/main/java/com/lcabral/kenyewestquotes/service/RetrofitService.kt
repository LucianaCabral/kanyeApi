package com.lcabral.kenyewestquotes.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {

    companion object {
        const val BASE_URL = "https://api.kanye.rest"
    }

    private fun quoteProvider(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun retrofitApi(): QuoteApi = quoteProvider().create(QuoteApi::class.java)
}
