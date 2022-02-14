package com.hamid.template.utils.dialogs.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.hamid.template.databinding.RowMapFormsBinding
import com.hamid.template.ui.mapScreen.models.ResponseDocumentList
import com.hamid.template.utils.dialogs.eventsListners.AllFormsListners

class AllFormsViewHolder(var binding: RowMapFormsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun setData(item: ResponseDocumentList.DataItem, moodBoosterListener: AllFormsListners) {
        item.run {
            binding.run {
                tvFormName.text=name
                tvSelectForm.setOnClickListener {
                    moodBoosterListener.onFormClicked(item)
                }
            }
        }
    }

}