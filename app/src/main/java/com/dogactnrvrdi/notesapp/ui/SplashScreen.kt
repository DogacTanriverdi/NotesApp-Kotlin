package com.dogactnrvrdi.notesapp.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.dogactnrvrdi.notesapp.R
import com.dogactnrvrdi.notesapp.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    private var runnable = Runnable {}
    private var handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val animFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.logoOpen.startAnimation(animFadeIn)

        supportActionBar?.hide()

        runnable = Runnable {
            val intent = Intent(
                this@SplashScreen,
                MainActivity::class.java
            )
            startActivity(intent)
            finish()
        }
        handler.postDelayed(runnable, 1500)
    }
}