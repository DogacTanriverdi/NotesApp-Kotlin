package com.dogactnrvrdi.notesapp.presentation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
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

