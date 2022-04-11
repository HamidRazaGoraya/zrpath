package com.hamid.template.utils.dialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;


import com.hamid.template.databinding.DialogUploadDocumentBinding;

import org.jetbrains.annotations.NotNull;

public class SelectUploadedDocument extends DialogFragment {

    private DialogUploadDocumentBinding binding;
    private Buttons buttons;
    public SelectUploadedDocument(Buttons buttons) {
        this.buttons=buttons;
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        binding=DialogUploadDocumentBinding.inflate(requireActivity().getLayoutInflater());
        binding.btnUploadDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttons.Upload();
                dismiss();
            }
        });
        binding.btnFillNewForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttons.AddNew();
                dismiss();
            }
        });
        binding.deleteDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        builder.setView(binding.getRoot());
        AlertDialog dialog=builder.create();
        if (dialog!= null && dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        return dialog;
    }
    public interface Buttons{

        void Upload();
        void AddNew();
    }


}