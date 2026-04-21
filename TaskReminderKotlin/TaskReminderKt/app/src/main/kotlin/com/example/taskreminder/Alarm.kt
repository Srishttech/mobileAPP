package com.example.taskreminder

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Alarm : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        val event      = findViewById<TextView>(R.id.event)
        val dismissBtn = findViewById<Button>(R.id.dismissBtn)

        // Task message dikhao
        val task = intent.getStringExtra("task")
        event.text = if (!task.isNullOrEmpty()) "⏰ $task" else "⏰ Time to check your task!"

        // Alarm sound bajao
        try {
            mediaPlayer = MediaPlayer.create(
                this,
                android.provider.Settings.System.DEFAULT_ALARM_ALERT_URI
            )?.apply {
                isLooping = true
                start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Dismiss
        dismissBtn.setOnClickListener {
            stopAlarm()
            finish()
        }
    }

    private fun stopAlarm() {
        mediaPlayer?.apply {
            stop()
            release()
        }
        mediaPlayer = null
    }

    override fun onDestroy() {
        super.onDestroy()
        stopAlarm()
    }
}
