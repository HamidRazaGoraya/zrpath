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
import com.hamid.template.utils.*
import com.hamid.template.utils.dialogs.eventsListners.AllFormsListners
import com.hamid.template.utils.dialogs.eventsListners.OnRefferalClicked

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
           binding.tripStatus.text=client.ChildTripStatus.localTranslation()
           binding.mainCard.setOnClickListener {
               onRefferalClicked.onPrepareClicked(visitListModel)
           }
           val checker=handleActivation(client)
           if (!checker){
               binding.tripStatus.text="Trip not Started"
               binding.startStop.makeVisible()
               binding.startStop.text="Prepare visit"
               binding.startStop.setOnClickListener {
                   onRefferalClicked.onPrepareClicked(visitListModel)
               }
               return
           }

           when(client.ChildTripStatus){
               "Trip not Started"->{
                   binding.startStop.makeVisible()
                   binding.startStop.text="Prepare visit"
                   binding.startStop.setOnClickListener {
                       onRefferalClicked.onPrepareClicked(visitListModel)
                   }
               }
               "IsPrepared"->{
                   binding.startStop.makeVisible()
                   binding.startStop.text="Start trip"
                   binding.startStop.setOnClickListener {
                       onRefferalClicked.onStartTripClicked(visitListModel)
                   }
               }
               "Trip Started"->{
                   binding.startStop.makeVisible()
                   binding.startStop.text="End trip"
                   binding.startStop.setOnClickListener {
                       onRefferalClicked.onEndTripClicked(visitListModel)
                   }
               }
               "Trip Completed"->{
                   binding.startStop.makeGone()
               }
           }


       }
    }

    fun handleActivation(client: TodayTripResponse.Data.Down.Client):Boolean{
        deactivateAll()
        binding.tripProgress.run {
            this.root.makeInvisible()
            prepare.progress("1",start = false,end = true)
            if (client.ChildTripStatus == "Trip not Started"){
               return false
            }
            this.root.makeVisible()
            prepare.activated("1",start = false,end = true)
            checkList.progress("2",start = true,end = true)

            if (!client.IsCheckListCompleted){
                return false
            }
            checkList.activated("2",start = true,end = true)
            missingForms.activated("3",start = true,end = true)
            signature.progress("4",start = true,end = false)
            if (client.PatientSignature==null){
                return  false
            }
            signature.activated("4",start = true,end = false)
            return true
        }
    }
    fun deactivateAll(){
        binding.tripProgress.run {
            prepare.deActivated("1", start = false, end = true)
            checkList.deActivated("2",start = true,end = true)
            missingForms.deActivated("3",start = true,end = true)
            signature.deActivated("4",start = true,end = false)
        }
    }

}