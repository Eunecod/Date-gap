package com.example.date_gap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import com.example.date_gap.service.DateHelper


class FragmentCalcDate : Fragment() {
    private var dateHelper: DateHelper = DateHelper()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_calc_date, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<EditText>(R.id.edit_base_date).doOnTextChanged { text, start, before, count ->
            dateHelper.format(view.findViewById<EditText>(R.id.edit_base_date), start, before, count)
        }
    }
}