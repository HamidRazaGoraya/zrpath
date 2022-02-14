package com.hamid.template.ui.checkList.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.hamid.template.databinding.RecycleCheckBoxBinding
import com.hamid.template.ui.mapScreen.models.UserCheckListResponse
import com.hamid.template.utils.AddRequired


class CheckListViewHolder(var binding: RecycleCheckBoxBinding) : RecyclerView.ViewHolder(binding.root) {

    fun setData(item: UserCheckListResponse.Data.Check) {
        item.run {
            binding.run {
                switch1.isChecked=isChecked
                switch1.text=detail.AddRequired()
            }
        }
    }




}