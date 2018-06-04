package com.example.anzendigital.calendartest

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.customtimepicker.extensions.month
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        calendar.layoutColor = Color.RED
        calendar.areWeekendsEnabled = false
        val cal = Calendar.getInstance()
        cal.month = 8
        calendar.setDateBounds(Date(), cal.time)
    }
}
