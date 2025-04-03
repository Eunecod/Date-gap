package com.example.date_gap.service

import android.annotation.SuppressLint
import android.widget.EditText


data class Date(val day: Int, val month: Int, val year: Int)

class DateHelper {

    @SuppressLint("SetTextI18n")
    fun format(dateText: EditText, start: Int, before: Int, count: Int) {
        val rawText = dateText.text.toString()
        val cleanText = rawText.replace("[^\\d]".toRegex(), "")

        if (cleanText.length > 8) {
            dateText.setText(rawText.substring(0, rawText.length - 1))
            dateText.setSelection(dateText.text?.length ?: 0)
            return
        }

        val formatted = buildString {
            if (cleanText.isNotEmpty()) {
                append(cleanText.substring(0, minOf(2, cleanText.length)))
                if (cleanText.length > 2) {
                    append(".").append(cleanText.substring(2, minOf(4, cleanText.length)))
                    if (cleanText.length > 4) {
                        append(".").append(cleanText.substring(4, minOf(8, cleanText.length)))
                    }
                }
            }
        }

        var currentPositionCursor = start

        if (cleanText.length > 2 && start <= 2 && before == 0 && count == 1) {
            currentPositionCursor += 2
        }
        if (cleanText.length > 4 && start <= 5 && before == 0 && count == 1) {
            currentPositionCursor += 2
        }
        if (before == 1 && (start == 3 || start == 6)) {
            currentPositionCursor -= 1
        }

        if (rawText != formatted) {
            dateText.setText(formatted)
            dateText.setSelection(minOf(currentPositionCursor, formatted.length))
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