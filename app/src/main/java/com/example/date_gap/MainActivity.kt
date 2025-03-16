package com.example.date_gap

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge

data class Date(val day: Int, val month: Int, val year: Int)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_calculate).setOnClickListener {
            val startDate = dateParse(findViewById<EditText>(R.id.edit_start_date).text.toString())
            val endDate = dateParse(findViewById<EditText>(R.id.edit_end_date).text.toString())

            val between = calculationDateGap(startDate, endDate)
            findViewById<TextView>(R.id.diff_day).text = between.toString()
        }
    }

    @SuppressLint("NewApi")
    fun calculationDateGap(start: Date?, end: Date?): Long {
        if (start != null && end != null) {
            return ChronoUnit.DAYS.between(LocalDate.of(start.year, start.month, start.day), LocalDate.of(end.year, end.month, end.day)) + 1
        }

        return 0;
    }

    private fun dateParse(dateStr: String): Date? {
        if(dateStr.length != 8) {
            return null
        }

        return Date(day=dateStr.substring(0, 2).toInt(), month=dateStr.substring(2, 4).toInt(), year=dateStr.substring(4, 8).toInt());
    }
}
