package com.hamid.template.ui.checkList.viewHolder

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.hamid.template.databinding.RecycleCheckBoxBinding
import com.hamid.template.ui.checkList.adopter.CheckListAdopter
import com.hamid.template.ui.mapScreen.models.UserCheckListResponse
import com.hamid.template.utils.AddRequired
import com.hamid.template.utils.setCheckListColor
import com.hamid.template.utils.setShowCondition


class CheckListViewHolder(var binding: RecycleCheckBoxBinding,var myClickListner: CheckListAdopter.myClickListner) : RecyclerView.ViewHolder(binding.root) {

    fun setData(item: UserCheckListResponse.Data.CheckListItem,int: Int) {
        item.run {
            binding.run {
                switch1.isChecked=isChecked
                switch1.text=detail.AddRequired()
                switch1.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked){
                        myClickListner.onChecked(item)
                    }else{
                        myClickListner.onUnChecked(item)
                    }
                }
                binding.tvSelectForm.setOnClickListener {
                    myClickListner.onFormClicked(item)
                }
                val isThisForm=!item.EBFormID.isNullOrEmpty()
                binding.switch1.isEnabled=!isThisForm
                binding.listBack.setCheckListColor(isThisForm)
                binding.rlStatus.setShowCondition(isThisForm)

            }
        }
    }




}