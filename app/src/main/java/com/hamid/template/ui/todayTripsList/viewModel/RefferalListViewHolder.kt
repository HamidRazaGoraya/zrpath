package com.hamid.template.ui.todayTripsList.viewModel

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hamid.template.databinding.RowMapFormsBinding
import com.hamid.template.databinding.RowTabVisitsBinding
import com.hamid.template.ui.dashboard.models.ResponseDashBoard
import com.hamid.template.ui.mapScreen.models.ResponseDocumentList
import com.hamid.template.ui.todayTripsList.models.ResponseReferralList
import com.hamid.template.utils.GetImageName
import com.hamid.template.utils.dialogs.eventsListners.AllFormsListners
import com.hamid.template.utils.dialogs.eventsListners.OnRefferalClicked
import com.hamid.template.utils.getRandomColor

class RefferalListViewHolder(var binding: RowTabVisitsBinding,var onRefferalClicked: OnRefferalClicked) :
    RecyclerView.ViewHolder(binding.root) {

    fun setData(item: ResponseDashBoard.Data.VisitItem) {
        binding.userName.text=item.referralName
        binding.root.setOnClickListener {
            onRefferalClicked.onClicked(item)
        }
        binding.userNameFirst.text=item.referralName.GetImageName()
        binding.UserImage.getRandomColor()
        binding.tripStatus.text=item.IsTripStarted
    }

}