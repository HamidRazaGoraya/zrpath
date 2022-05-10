package com.hamid.template.ui.todayTripDetails

import com.hamid.template.base.ViewInteractor
import com.hamid.template.ui.todayTripDetails.models.ResponseOnGoingVisit
import com.repsly.library.timelineview.TimelineView


interface TodayTripDetailsContracts : ViewInteractor {
    fun finishScreen()
    fun setData()
    fun setUpView()
    fun HideLoading()
    fun ShowLoading()
    fun allDeactivate()
    fun activeBeginPrepare()
    fun activeCheckListPick()
    fun activeMissingForm()
    fun activeSignature()

    fun activeTimeLine(timelineView: TimelineView,string: String,start: Boolean,end: Boolean)
    fun disableTimeLine(timelineView: TimelineView,string: String,start: Boolean,end: Boolean)
    fun inProgressTimeLine(timelineView: TimelineView,string: String,start: Boolean,end: Boolean)

    fun clickedBeginPrepare()
    fun clickedPickUpCheckList()
    fun clickedMissingForm()
    fun clickedDropOfSignature()
    fun finishTrip()

    fun loadVisitDetails()
    fun fillUserDetails(data:ResponseOnGoingVisit.Data)

    fun loadTripCompletedView()
    fun loadAllPointsCompleted()
}