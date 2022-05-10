package com.hamid.template.ui.loginAndRegister.customDialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;


import com.hamid.template.databinding.DialogChangeApiBinding;
import com.hamid.template.ui.loginAndRegister.RegisterActivity;
import com.hamid.template.utils.Constants;
import com.hamid.template.utils.ConstantsJava;

import org.jetbrains.annotations.NotNull;

public class EditCurrentAPI extends DialogFragment {

    private DialogChangeApiBinding binding;
    public EditCurrentAPI() {

    }

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        binding=DialogChangeApiBinding.inflate(requireActivity().getLayoutInflater());

        binding.CurrentAPI.setText(new ConstantsJava().getBaseURl(requireContext()));
        binding.SaveNewAPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=binding.CurrentAPI.getText().toString();
                if (text.isEmpty()){
                    binding.CurrentAPI.setError("Required");
                    binding.CurrentAPI.requestFocus();
                    return;
                }
                new ConstantsJava().setBaseURl(requireContext(),text);
                Toast.makeText(requireContext(), "Remove application from history and open", Toast.LENGTH_SHORT).show();
                requireActivity().finishAffinity();
            }
        });
        binding.ResetAPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ConstantsJava().resetBaseURl(requireContext());
                Toast.makeText(requireContext(), "Remove application from history and open", Toast.LENGTH_SHORT).show();
                requireActivity().finishAffinity();
            }
        });
        binding.closeList.setOnClickListener(new View.OnClickListener() {
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


}