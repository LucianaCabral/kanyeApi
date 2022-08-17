package com.lcabral.kenyewestquotes.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.lcabral.kenyewestquotes.R
import com.lcabral.kenyewestquotes.databinding.ActivityMainBinding
import com.lcabral.kenyewestquotes.repository.QuoteRepository
import com.lcabral.kenyewestquotes.service.RetrofitService
import com.lcabral.kenyewestquotes.viewmodel.QuoteViewModel
import com.lcabral.kenyewestquotes.viewmodel.QuoteViewModelFactory

class QuoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: QuoteViewModel
    private lateinit var quoteRepository: QuoteRepository
    private lateinit var retrofitService: RetrofitService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        loadingVisibility(true)
        getQuotes()
        getQuoteButtonClickListener()
    }

    private fun setupViewModel() {
        retrofitService = RetrofitService()
        quoteRepository = QuoteRepository(retrofitService)
        viewModel = ViewModelProvider(
            this, QuoteViewModelFactory(quoteRepository)
        )[QuoteViewModel::class.java]
    }

    private fun getQuoteButtonClickListener() {
        binding.btnGetQuote.setOnClickListener { getQuotes() }
    }

    private fun getQuotes() {
        try {
            viewModel.init()
            viewModel.quotes.observe(this) {
                if (it.isSuccessful) {
                    binding.tvQuote.text = it.body()?.quote
                    loadingVisibility(false)
                } else {
                    Log.d(getString(R.string.response), it.errorBody().toString())
                }
            }
        } catch (e: Exception) {
            binding.tvQuote.text = e.toString()
        }
    }

    private fun loadingVisibility(isLoading: Boolean) {
        binding.progressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}