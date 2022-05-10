package com.hamid.template.ui.todayTripDetails

import com.hamid.template.base.BaseViewModel
import com.hamid.template.network.ApiRepository
import com.hamid.template.ui.dashboard.models.VisitListModel
import com.hamid.template.ui.facilitiesPatiensts.models.RequestSetTime
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

    lateinit var visitListModel: VisitListModel
    fun initThings() {
        viewInteractor?.setUpView()
    }

    fun loadVisitDetails()=viewInteractor?.loadVisitDetails()

    fun onBackClick() {
        viewInteractor?.finishScreen()
    }
    var currentActive=1

    var data:ResponseOnGoingVisit.Data?=null

    fun allDeactivate()=viewInteractor?.allDeactivate()
    fun activePickUp()=viewInteractor?.activeBeginPrepare()
    fun activeCheckListPick()=viewInteractor?.activeCheckListPick()
    fun activeMissingForm()=viewInteractor?.activeMissingForm()
    fun activeCheckListDrop()=viewInteractor?.activeSignature()
    fun disableTimeLine(timelineView: TimelineView,string: String,start: Boolean,end: Boolean)=viewInteractor?.disableTimeLine(timelineView,string, start, end)
    fun activeTimeLine(timelineView: TimelineView,string: String,start: Boolean,end: Boolean)=viewInteractor?.activeTimeLine(timelineView,string,start,end)
    fun inProgressTimeLine(timelineView: TimelineView,string: String,start: Boolean,end: Boolean)=viewInteractor?.inProgressTimeLine(timelineView,string, start, end)

    fun handleActivation(){
        val int=currentActive

        viewInteractor?.allDeactivate()
        if (int>0){
            viewInteractor?.activeBeginPrepare()
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
            viewInteractor?.loadAllPointsCompleted()
        }
        if (int>5){
            viewInteractor?.loadTripCompletedView()
        }
    }


    fun clickedBeginPrepare()=viewInteractor?.clickedBeginPrepare()
    fun clickedPickUpCheckList()=viewInteractor?.clickedPickUpCheckList()
    fun clickedMissingForm()=viewInteractor?.clickedMissingForm()
    fun clickedDropOfSignature()=viewInteractor?.clickedDropOfSignature()


    fun OnGoingVisit(ScheduleID:Int,referralID:Int,transportationGroupID:Int)=apiRepository.OnGoingVisit(ScheduleID,referralID,transportationGroupID)

    fun HideLoading()=viewInteractor?.HideLoading()
    fun ShowLoading()=viewInteractor?.ShowLoading()

    fun saveTTime(data: RequestSetTime.Data)=apiRepository.saveTTime(data)
    fun fillUserDetails(data:ResponseOnGoingVisit.Data)=viewInteractor?.fillUserDetails(data)
    fun saveUserSignature(TransportVisitIDValue:Int,ReferralIDValue:Int,ScheduleIDValue:Int,file: File)=apiRepository.saveUserSignature(TransportVisitIDValue, ReferralIDValue, ScheduleIDValue, file)


    fun loadTripCompletedView()=viewInteractor?.loadTripCompletedView()

    fun getScheduleID():Int?{
        return visitListModel.client?.scheduleID
    }
}