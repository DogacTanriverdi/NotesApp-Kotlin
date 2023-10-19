package com.dogactnrvrdi.notesapp.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.dogactnrvrdi.notesapp.R

class SplashScreen : AppCompatActivity() {

    private var runnable = Runnable {}
    private var handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()

        if (Build.VERSION.SDK_INT < 32) {
            runnable = Runnable {
                val intent = Intent(
                    this@SplashScreen,
                    MainActivity::class.java
                )
                startActivity(intent)
                finish()
            }
            handler.postDelayed(runnable, 1000)
        } else {
            val intent = Intent(
                this@SplashScreen,
                MainActivity::class.java
            )
            startActivity(intent)
            finish()
        }
    }
}