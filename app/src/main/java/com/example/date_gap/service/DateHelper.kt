package com.example.date_gap.service

import android.annotation.SuppressLint
import android.widget.EditText


data class Date(val day: Int, val month: Int, val year: Int)

class DateHelper {
    @SuppressLint("SetTextI18n")
    fun format(dateText: EditText, start: Int, before: Int, count: Int) {
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

    fun parse(dateStr: String): Date? {
        if(dateStr.length != 10) {
            return null
        }

        return Date(day=dateStr.substring(0, 2).toInt(), month=dateStr.substring(3, 5).toInt(), year=dateStr.substring(6, 10).toInt())
    }

    fun addDays(date: Date, daysToAdd: Int): String {
        var day = date.day
        var month = date.month
        var year = date.year

        var remainingDays = daysToAdd - 1

        while (remainingDays > 0) {
            val daysInMonth = getDaysInMonth(month, year)
            val daysLeftInMonth = daysInMonth - day

            if (remainingDays > daysLeftInMonth) {
                remainingDays -= (daysLeftInMonth + 1)
                day = 1
                month++
                if (month > 12) {
                    month = 1
                    year++
                }
            } else {
                day += remainingDays
                remainingDays = 0
            }
        }

        return "%02d.%02d.%04d".format(day, month, year)
    }

    private fun getDaysInMonth(month: Int?, year: Int): Int {
        return when (month) {
            4, 6, 9, 11 -> 30
            2 -> if (isLeapYear(year)) 29 else 28
            else -> 31
        }
    }

    private fun isLeapYear(year: Int): Boolean {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
    }
}