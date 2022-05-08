package com.hamid.template.ui.todayTripsList.viewModel

import androidx.recyclerview.widget.RecyclerView
import com.hamid.template.databinding.HeadingVisitsBinding
import com.hamid.template.ui.facilitiesPatiensts.models.TodayTripResponse
import com.hamid.template.utils.dialogs.eventsListners.OnRefferalClicked

class HeadingListViewHolder(var binding: HeadingVisitsBinding,var onRefferalClicked: OnRefferalClicked) :
    RecyclerView.ViewHolder(binding.root) {

    fun setData(group: TodayTripResponse.Data.Down.TransportationGroup) {

        binding.facilityLocation.setText(group.location)
        binding.facilityHouse.setText(group.facilityName)

        binding.facilityDetails.setText("${group.staffNames}\n(${group.facilityName}|${group.groupLocation} | ${group.tripDirection})")

        val tripStatus = group.GroupTripStatus
        when (tripStatus) {
            "Trip not started" -> {
                tripStartView(binding, group)
            }
            "Trip not start" -> {
                tripStartView(binding, group)
            }
            "Trip Started" -> {
                tripEndView(binding, group)
            }
            else -> {
                tripCompletedView(binding)
            }

        }
    }

    fun tripStartView(binding: HeadingVisitsBinding, group:TodayTripResponse.Data.Down.TransportationGroup){
        binding.startStop.isEnabled=true
        binding.startStop.text="Start group trip"
        binding.startStop.setOnClickListener {
            onRefferalClicked.onGroupTripStart(group)
        }
    }
    fun tripEndView(binding: HeadingVisitsBinding, group:TodayTripResponse.Data.Down.TransportationGroup){
        binding.startStop.isEnabled=true
        binding.startStop.text="End group trip"
        binding.startStop.setOnClickListener {
            onRefferalClicked.onGroupTripEnd(group)
        }
    }
    fun tripCompletedView(binding: HeadingVisitsBinding){
        binding.startStop.isEnabled=false
        binding.startStop.text="Completed"
    }

}