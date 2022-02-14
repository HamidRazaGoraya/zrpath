package com.hamid.template.utils.dialogs

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.graphics.drawable.ColorDrawable
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.hamid.template.databinding.CalenderDatePickerBinding
import java.util.*

class CalenderDatePicker(private val onSelected: OnSelected, private val calendar: Calendar) :
    DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        // Get the layout inflater
        val inflater = requireActivity().layoutInflater
        val binding = CalenderDatePickerBinding.inflate(inflater)
        binding.calendar.date = calendar.timeInMillis
        binding.deleteDate.setOnClickListener {
            dismiss()
        }
        binding.calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            calendar[Calendar.YEAR] = year
            calendar[Calendar.MONTH] = month
            calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
        }
        binding.Approve.setOnClickListener {
            onSelected.Selected(calendar)
            dismiss()
        }
        binding.Cancel.setOnClickListener { dismiss() }
        builder.setView(binding.root)
        val dialog = builder.create()
        if (dialog.window != null) {
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }
        return dialog
    }

    interface OnSelected {
        fun Selected(calendar: Calendar)
    }

    override fun onStart() {
        super.onStart()
    }
}