package com.hamid.template.utils.dialogs

import android.app.Dialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.hamid.template.R
import com.hamid.template.databinding.DialogUploadDocumentBinding

class SelectUploadedDocument(private val buttons: Buttons,var formEnable: Boolean) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        var binding = DialogUploadDocumentBinding.inflate(requireActivity().layoutInflater)
        binding.btnUploadDocument.setOnClickListener {
            buttons.Upload()
            dismiss()
        }
        binding.btnUploadDocument.isEnabled=!formEnable
        binding.btnFillNewForm.isEnabled=formEnable
        if (formEnable){
            binding.btnUploadDocument.backgroundTintList= ColorStateList.valueOf(requireContext().resources.getColor(R.color.color_divider))
        }else{
            binding.btnFillNewForm.backgroundTintList= ColorStateList.valueOf(requireContext().resources.getColor(R.color.color_divider))
        }
        binding.btnFillNewForm.setOnClickListener {
            buttons.AddNew()
            dismiss()
        }
        binding.deleteDate.setOnClickListener { dismiss() }
        builder.setView(binding.root)
        val dialog = builder.create()
        if (dialog != null && dialog.window != null) {
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }
        return dialog
    }

    interface Buttons {
        fun Upload()
        fun AddNew()
    }

}