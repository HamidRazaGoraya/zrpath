package com.hamid.template.ui.todayTripsList.viewModel

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hamid.template.databinding.HeadingVisitsBinding
import com.hamid.template.databinding.RowMapFormsBinding
import com.hamid.template.databinding.RowTabVisitsBinding
import com.hamid.template.ui.dashboard.models.ResponseDashBoard
import com.hamid.template.ui.mapScreen.models.ResponseDocumentList
import com.hamid.template.ui.todayTripsList.models.ResponseReferralList
import com.hamid.template.utils.GetImageName
import com.hamid.template.utils.dialogs.eventsListners.AllFormsListners
import com.hamid.template.utils.dialogs.eventsListners.OnRefferalClicked
import com.hamid.template.utils.getRandomColor

class HeadingListViewHolder(var binding: HeadingVisitsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun setData(heading:String) {
        binding.title.text=heading
    }

}