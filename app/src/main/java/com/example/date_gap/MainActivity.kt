package com.example.date_gap

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.date_gap.service.DateHelper
import com.example.date_gap.service.Date
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : FragmentActivity() {
    private lateinit var adapter: ViewPageAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    private var dateHelper: DateHelper = DateHelper()
    private val tabNames: Array<String> = arrayOf(
        "Кол-во дней",
        "Расчёт даты"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_main)

        adapter = ViewPageAdapter(this)
        viewPager = findViewById<ViewPager2>(R.id.view_pager)
        viewPager.adapter = adapter

        tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        TabLayoutMediator(tabLayout, viewPager) {tab, position ->
           tab.text = tabNames[position]
        }.attach()

        findViewById<Button>(R.id.btn_calculate).setOnClickListener {
            if(tabLayout.selectedTabPosition == 0) {
                val startDate = dateHelper.parse(findViewById<EditText>(R.id.edit_start_date).text.toString())
                val endDate = dateHelper.parse(findViewById<EditText>(R.id.edit_end_date).text.toString())

                val between = calculationDateGap(startDate, endDate)
                findViewById<TextView>(R.id.diff_day).text = between.toString()
            }
            else if(tabLayout.selectedTabPosition == 1) {
                val baseDate = dateHelper.parse(findViewById<EditText>(R.id.edit_base_date).text.toString())
                val countDay = findViewById<EditText>(R.id.edit_count_day).text.toString()

                if(baseDate != null && countDay != "") {
                    findViewById<TextView>(R.id.calc_date).text = dateHelper.addDays(baseDate, countDay.toInt())
                }
            }
        }
    }

    @SuppressLint("NewApi")
    fun calculationDateGap(start: Date?, end: Date?): Long {
        if (start != null && end != null && start != end) {
            return ChronoUnit.DAYS.between(LocalDate.of(start.year, start.month, start.day), LocalDate.of(end.year, end.month, end.day)) + 1
        }

        return 0
    }
}