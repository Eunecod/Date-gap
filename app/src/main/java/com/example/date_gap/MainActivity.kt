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
import androidx.core.widget.doOnTextChanged

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

        findViewById<EditText>(R.id.edit_start_date).doOnTextChanged { text, start, before, count ->
            dateEditFormat(findViewById<EditText>(R.id.edit_start_date), start, before, count)
        }

        findViewById<EditText>(R.id.edit_end_date).doOnTextChanged { text, start, before, count ->
            dateEditFormat(findViewById<EditText>(R.id.edit_end_date), start, before, count)
        }
    }

    @SuppressLint("NewApi")
    fun calculationDateGap(start: Date?, end: Date?): Long {
        if (start != null && end != null && start != end) {
            return ChronoUnit.DAYS.between(LocalDate.of(start.year, start.month, start.day), LocalDate.of(end.year, end.month, end.day)) + 1
        }

        return 0
    }

    private fun dateParse(dateStr: String): Date? {
        if(dateStr.length != 10) {
            return null
        }

        return Date(day=dateStr.substring(0, 2).toInt(), month=dateStr.substring(3, 5).toInt(), year=dateStr.substring(6, 10).toInt())
    }

    @SuppressLint("SetTextI18n")
    private fun dateEditFormat(dateText: EditText, start: Int, before: Int, count: Int) {
        val rawStr = dateText.text.toString()
        dateText.setSelection(rawStr.length)

        if (rawStr.length > 10) {
            dateText.setText(rawStr.substring(0, 10))
            return
        }

        if (rawStr.length == 2 && before == 0 && count == 1) {
            dateText.setText(rawStr.substring(0, 2) + ".")
        }

        if (rawStr.length == 5 && before == 0 && count == 1) {
            dateText.setText(rawStr.substring(0, 5) + ".")
        }

        if (before == 1 && count == 0 && (start == 2 || start == 5)) {
            dateText.setText(rawStr.substring(0, start - 1) + rawStr.substring(start))
            dateText.setSelection(start - 1)
        }
    }
}