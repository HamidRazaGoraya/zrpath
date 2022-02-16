package com.hamid.template.utils.dialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.hamid.template.databinding.DialogMapFormsBinding;
import com.hamid.template.ui.mapScreen.models.ResponseDocumentList;
import com.hamid.template.utils.dialogs.adopter.AllFormsAdopter;
import com.hamid.template.utils.dialogs.eventsListners.AllFormsListners;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AllFormsList extends DialogFragment {
    private   Buttons buttons;
    private DialogMapFormsBinding binding;

    private ArrayList<ResponseDocumentList.DataItem> careTypeList = new ArrayList<>();
    public AllFormsList(Buttons buttons, List<ResponseDocumentList.DataItem> careTypeList) {
        this.buttons = buttons;
        this.careTypeList.clear();
        this.careTypeList.addAll(careTypeList);
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        binding=DialogMapFormsBinding.inflate(requireActivity().getLayoutInflater());
        binding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dismiss();
            }
        });

        AllFormsAdopter adapter = new AllFormsAdopter();
        binding.rvMapForms.setAdapter(adapter);
        binding.rvMapForms.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.setListener(new AllFormsListners() {
            @Override
            public void onFormClicked(@NonNull ResponseDocumentList.DataItem item) {
                buttons.CareTypeClicked(item);
                dismiss();
            }
        });
        adapter.updateItems(careTypeList);
        binding.edtSearchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                  adapter.getFilter().filter(binding.edtSearchText.getText().toString());
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

        void CareTypeClicked(ResponseDocumentList.DataItem careTypeId);
    }


}