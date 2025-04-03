package com.example.date_gap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import com.example.date_gap.service.DateHelper


class FragmentNumberDay : Fragment() {
    private var dateHelper: DateHelper = DateHelper()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_number_day, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<EditText>(R.id.edit_start_date).doOnTextChanged { _, start, before, count ->
            dateHelper.format(view.findViewById<EditText>(R.id.edit_start_date), start, before, count)
        }

        view.findViewById<EditText>(R.id.edit_end_date).doOnTextChanged { _, start, before, count ->
            dateHelper.format(view.findViewById<EditText>(R.id.edit_end_date), start, before, count)
        }
    }
}