package com.hamid.template.ui.todayTripsList.viewModel

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hamid.template.databinding.RowMapFormsBinding
import com.hamid.template.databinding.RowTabVisitsBinding
import com.hamid.template.ui.dashboard.models.ResponseDashBoard
import com.hamid.template.ui.dashboard.models.VisitListModel
import com.hamid.template.ui.facilitiesPatiensts.models.TodayTripResponse
import com.hamid.template.ui.mapScreen.models.ResponseDocumentList
import com.hamid.template.ui.todayTripsList.models.ResponseReferralList
import com.hamid.template.utils.GetImageName
import com.hamid.template.utils.dialogs.eventsListners.AllFormsListners
import com.hamid.template.utils.dialogs.eventsListners.OnRefferalClicked
import com.hamid.template.utils.getRandomColor

class RefferalListViewHolder(var binding: RowTabVisitsBinding,var onRefferalClicked: OnRefferalClicked) :
    RecyclerView.ViewHolder(binding.root) {

    fun setData(visitListModel: VisitListModel) {
       visitListModel.client?.let {client->
           binding.userName.text=client.name
           binding.root.setOnClickListener {
               onRefferalClicked.onClicked(visitListModel)
           }
           binding.userNameFirst.text=client.name.GetImageName()
           binding.UserImage.getRandomColor()
           binding.tripStatus.text=client.ChildTripStatus
           if (client.PatientSignature==null){
               binding.startStop.text="Prepare visit"
               binding.startStop.setOnClickListener {
                   onRefferalClicked.onPrepareClicked(visitListModel)
               }
               return
           }
           when(client.ChildTripStatus){
               "Trip not started"->{
                   binding.startStop.text="Start trip"
                   binding.startStop.setOnClickListener {
                       onRefferalClicked.onStartTripClicked(visitListModel)
                   }
               }
           }
       }
    }

}