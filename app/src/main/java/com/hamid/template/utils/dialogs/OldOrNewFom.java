package com.hamid.template.utils.dialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.hamid.template.databinding.CustomDialogImagePockerBinding;
import com.hamid.template.databinding.OldOrNewBinding;

import org.jetbrains.annotations.NotNull;

public class OldOrNewFom extends DialogFragment {

    private OldOrNewBinding binding;
    private Buttons buttons;
    private String title;
    public OldOrNewFom(Buttons buttons, String title) {
       this.buttons=buttons;
       this.title=title;
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        binding=OldOrNewBinding.inflate(requireActivity().getLayoutInflater());
        binding.txtDialogIpTitle.setText(title);
        binding.layDialogCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttons.NewForm();
                dismiss();
            }
        });
        binding.layDialogGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttons.OldForm();
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
        void NewForm();
        void OldForm();
    }


}