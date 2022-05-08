package com.hamid.template.ui.medicationFormsList.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.hamid.template.databinding.RowSectionListBinding
import com.hamid.template.databinding.RowSectionListMissingBinding
import com.hamid.template.ui.medicationFormsList.events.MedicationFormClicked
import com.hamid.template.ui.medicationFormsList.model.ResponseMedicationFormsList
import com.hamid.template.ui.missingForms.eventListners.FormClicked
import com.hamid.template.ui.missingForms.model.ResponseMissingDocument
import com.hamid.template.utils.setRandomTint

class MedicationFormsListViewHolder(var binding: RowSectionListMissingBinding, var formClicked: MedicationFormClicked) :
    RecyclerView.ViewHolder(binding.root) {

    fun setData(item: ResponseMedicationFormsList.DataItem) {
        binding.tvSection.text=item.fileName
        binding.root.setOnClickListener {
            formClicked.openClicked(item)
        }
        binding.llEdit.setOnClickListener {
            formClicked.editClicked(item)
        }
        binding.llDelete.setOnClickListener {
            formClicked.deleteClicked(item)
        }
        binding.ivSection.setRandomTint()
    }

}