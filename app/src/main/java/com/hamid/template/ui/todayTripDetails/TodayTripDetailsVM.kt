package com.hamid.template.ui.todayTripDetails

import com.hamid.template.base.BaseViewModel
import com.hamid.template.network.ApiRepository
import com.hamid.template.ui.dashboard.models.ResponseDashBoard
import com.hamid.template.ui.todayTripDetails.models.ResponseOnGoingVisit
import com.repsly.library.timelineview.TimelineView
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class TodayTripDetailsVM @Inject
constructor(
    private val apiRepository: ApiRepository,
) : BaseViewModel<TodayTripDetailsContracts>() {

    lateinit var visitItem: ResponseDashBoard.Data.VisitItem
    fun initThings() {
        viewInteractor?.setUpView()
        viewInteractor?.checkGroupStatus()
    }

    fun checkGroupStatus()=viewInteractor?.checkGroupStatus()
    fun loadVisitDetails()=viewInteractor?.loadVisitDetails()

    fun onBackClick() {
        viewInteractor?.finishScreen()
    }
    var currentActive=1

    var data:ResponseOnGoingVisit.Data?=null

    fun allDeactive()=viewInteractor?.allDeactive()
    fun activePickUp()=viewInteractor?.activePickUp()
    fun activeCheckListPick()=viewInteractor?.activeCheckListPick()
    fun activeMissingForm()=viewInteractor?.activeMissingForm()
    fun activeCheckListDrop()=viewInteractor?.activeSignature()
    fun activeDrop()=viewInteractor?.activeDrop()
    fun disableTimeLine(timelineView: TimelineView,string: String,start: Boolean,end: Boolean)=viewInteractor?.disableTimeLine(timelineView,string, start, end)
    fun activeTimeLine(timelineView: TimelineView,string: String,start: Boolean,end: Boolean)=viewInteractor?.activeTimeLine(timelineView,string,start,end)

    fun handleActivation(){
        val int=currentActive

        viewInteractor?.allDeactive()
        if (int>0){
            viewInteractor?.activePickUp()
        }
        if (int>1){
            viewInteractor?.activeCheckListPick()
        }
        if (int>2){
            viewInteractor?.activeMissingForm()
        }
        if (int>3){
            viewInteractor?.activeSignature()
        }
        if (int>4){
            viewInteractor?.activeDrop()
        }
        if (int>5){
            viewInteractor?.loadTripCompletedView()
        }

        visitItem.GroupTripStatus?.let {
            when(it){
                "Trip Completed"->{
                    loadGroupTripCompleted()
                }
                else->{
                }
            }
        }
    }


    fun clickedStartTrip()=viewInteractor?.clickedStartTrip()
    fun clickedPickUpCheckList()=viewInteractor?.clickedPickUpCheckList()
    fun clickedMissingForm()=viewInteractor?.clickedMissingForm()
    fun clickedDropOfSignature()=viewInteractor?.clickedDropOfSignature()
    fun clickedDrop()=viewInteractor?.clickedDrop()

    fun OnGoingVisit(ScheduleID:Int,referralID:Int)=apiRepository.OnGoingVisit(ScheduleID,referralID)

    fun HideLoading()=viewInteractor?.HideLoading()
    fun ShowLoading()=viewInteractor?.ShowLoading()


    fun loadGroupNotStartView()=viewInteractor?.loadGroupNotStartView()
    fun loadGroupStartedView()=viewInteractor?.loadGroupStartedView()
    fun loadTripCompletedView()=viewInteractor?.loadTripCompletedView()
    fun loadGroupTripCompleted()=viewInteractor?.loadGroupTripCompleted()
    fun fillUserDetails(data:ResponseOnGoingVisit.Data)=viewInteractor?.fillUserDetails(data)
    fun saveUserSignature(TransportVisitIDValue:Int,ReferralIDValue:Int,ScheduleIDValue:Int,file: File)=apiRepository.saveUserSignature(TransportVisitIDValue, ReferralIDValue, ScheduleIDValue, file)
}