package com.hamid.template.ui.todayTripDetails.dialogs

import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.hamid.template.databinding.DrropSignDialogBinding
import com.hamid.template.ui.todayTripDetails.models.ResponseOnGoingVisit
import com.hamid.template.utils.JAVAUtilities
import java.util.*

class DropSignHere(private val onSelected: OnSelected,val data: ResponseOnGoingVisit.Data?) :
    DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        // Get the layout inflater
        val inflater = requireActivity().layoutInflater
        val binding = DrropSignDialogBinding.inflate(inflater)

        binding.clearSignature.setOnClickListener {
            binding.signatureViewEdit.clearCanvas()
        }
        binding.Approve.setOnClickListener {
            if (Verifay(binding)){
                 onSelected.SignatureAdded(binding.signatureViewEdit.signatureBitmap)
                 dismiss()
            }else{
                onSelected.SignatureNotAdded()
            }
        }
        binding.closeWindow.setOnClickListener { dismiss() }
        binding.Cancel.setOnClickListener { dismiss() }

        data?.onGoingVisitDetail?.PatientSignature?.let {
                     try {
                         binding.signatureViewEdit.clearCanvas()
                         JAVAUtilities.convertBase64ToBitmap(it)?.let {
                             binding.signatureViewEdit.setBitmap(it)
                         }
                     }catch (e:Exception){
                         e.printStackTrace()
                     }
        }

        builder.setView(binding.root)
        val dialog = builder.create()
        if (dialog.window != null) {
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }
        return dialog
    }

    interface OnSelected {
        fun SignatureAdded(bitmap: Bitmap)
        fun SignatureNotAdded()
    }

    override fun onStart() {
        super.onStart()
    }

    fun Verifay(binding:DrropSignDialogBinding):Boolean{
        if (binding.signatureViewEdit.isBitmapEmpty){
            return false
        }
        if (binding.signatureViewEdit.signatureBitmap==null){
            return false
        }
        return true
    }


}