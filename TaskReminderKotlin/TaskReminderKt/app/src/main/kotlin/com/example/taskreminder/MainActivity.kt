package com.example.taskreminder

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 2 second splash, phir ReminderActivity
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, ReminderActivity::class.java))
            finish()
        }, 2000)
    }
}
