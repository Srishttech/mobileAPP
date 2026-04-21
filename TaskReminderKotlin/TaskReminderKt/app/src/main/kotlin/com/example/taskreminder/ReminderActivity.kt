package com.example.taskreminder

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class ReminderActivity : AppCompatActivity() {

    private lateinit var editTitle: EditText
    private lateinit var btnDate: Button
    private lateinit var btnTime: Button
    private lateinit var btnSubmit: Button

    private var selectedYear = 0
    private var selectedMonth = 0
    private var selectedDay = 0
    private var selectedHour = 0
    private var selectedMinute = 0
    private var dateSet = false
    private var timeSet = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)

        editTitle = findViewById(R.id.editTitle)
        btnDate   = findViewById(R.id.btnDate)
        btnTime   = findViewById(R.id.btnTime)
        btnSubmit = findViewById(R.id.btnSubmit)

        // Date Picker
        btnDate.setOnClickListener {
            val cal = Calendar.getInstance()
            DatePickerDialog(this, { _, year, month, day ->
                selectedYear  = year
                selectedMonth = month
                selectedDay   = day
                dateSet = true
                btnDate.text = "$day/${month + 1}/$year"
            }, cal.get(Calendar.YEAR),
               cal.get(Calendar.MONTH),
               cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        // Time Picker
        btnTime.setOnClickListener {
            val cal = Calendar.getInstance()
            TimePickerDialog(this, { _, hour, minute ->
                selectedHour   = hour
                selectedMinute = minute
                timeSet = true
                btnTime.text = String.format("%02d:%02d", hour, minute)
            }, cal.get(Calendar.HOUR_OF_DAY),
               cal.get(Calendar.MINUTE), true).show()
        }

        // Save / Set Alarm
        btnSubmit.setOnClickListener {
            val task = editTitle.text.toString().trim()

            when {
                task.isEmpty() -> {
                    Toast.makeText(this, "Please enter a task!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                !dateSet -> {
                    Toast.makeText(this, "Please select a date!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                !timeSet -> {
                    Toast.makeText(this, "Please select a time!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            val alarmCal = Calendar.getInstance().apply {
                set(selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute, 0)
            }

            if (alarmCal.timeInMillis <= System.currentTimeMillis()) {
                Toast.makeText(this, "Please select a future time!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, AlarmReceiver::class.java).apply {
                putExtra("task", task)
            }

            val pendingIntent = PendingIntent.getBroadcast(
                this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmCal.timeInMillis, pendingIntent)

            Toast.makeText(this, "Reminder Saved! ✅", Toast.LENGTH_LONG).show()

            // Reset fields
            editTitle.setText("")
            btnDate.text = "DATE"
            btnTime.text = "TIME"
            dateSet = false
            timeSet = false
        }
    }
}
