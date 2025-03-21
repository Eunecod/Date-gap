package com.example.date_gap

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class ViewPageAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2;

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FragmentNumberDay()
            1 -> FragmentCalcDate()
            else -> throw IllegalStateException("Invalid position: $position")
        }
    }
}