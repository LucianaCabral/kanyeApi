package com.lcabral.kenyewestquotes.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.lcabral.kenyewestquotes.databinding.ActivitySplashScreenBinding

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private val splashScreen: ImageView by lazy { binding.splashscreen }
    private val startv: TextView by lazy { binding.tvStar }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupSplash()

        startv.text.toString()
    }

    private fun setupSplash() {
        splashScreen.alpha = 0f
        splashScreen.animate().setDuration(4000).alpha(1f).withEndAction {
            val intent = Intent(this, QuoteActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}